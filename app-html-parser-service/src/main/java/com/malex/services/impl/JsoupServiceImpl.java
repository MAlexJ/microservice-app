package com.malex.services.impl;

import com.malex.exceptions.JsoupServiceException;
import com.malex.models.base.BillStatus;
import com.malex.services.JsoupService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class JsoupServiceImpl implements JsoupService {
    private final static String NAV_TAB1_ELEMENT = "nav-tab1";
    private final static String TBODY_ELEMENT = "tbody";
    private final static String TR_ELEMENT = "tr";
    private final static String TD_ELEMENT = "td";

    /**
     * Error messages
     */
    private final static String ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND = "Element by tag - '%s' was not found";
    private final static String ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND = "Elements by tag - '%s' was not found";


    @Override
    public Flux<BillStatus> processHtmlRequest(String html) {
            Document document = Jsoup.parse(html);
            Element tableElement = findNavTab1Element(document, Element::firstElementChild);
            Element tbody = findTBodyElement(tableElement, Elements::first);
            Elements trElements = findTrElements(tbody);
            List<BillStatus> result = findTableElements(trElements);
            return Flux.fromIterable(result);

    }


    private Element findNavTab1Element(Element element, Function<Element, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementById(NAV_TAB1_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, NAV_TAB1_ELEMENT));
    }


    private Element findTBodyElement(Element element, Function<Elements, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TBODY_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TBODY_ELEMENT));
    }


    private Elements findTrElements(Element element) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TR_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, TR_ELEMENT));
    }


    private List<BillStatus> findTableElements(Elements elements) {
        return elements.stream() //
                .map(this::findTdElements) //
                .map(this::buildModel) //
                .collect(Collectors.toList());
    }


    private Elements findTdElements(Element els) {
        return Optional.of(els.getElementsByTag(TD_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TD_ELEMENT));
    }


    private String getElementText(Elements elements, String tag, Function<Elements, Element> fn) {
        Element element = fn.apply(elements);
        return Optional.ofNullable(element) //
                .map(Element::text) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, tag));

    }


    private BillStatus buildModel(Elements tdElements) {
        return BillStatus.builder() //
                .data(getElementText(tdElements, "first -> data", Elements::first)) //
                .status(getElementText(tdElements, "last -> status", Elements::last)) //
                .build();
    }


    private Supplier<JsoupServiceException> buildError(String errorMessage, String tag) {
        return () -> new JsoupServiceException(String.format(errorMessage, tag));
    }

}
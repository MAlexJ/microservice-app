package com.malex.services.impl;

import com.malex.exceptions.JsoupServiceException;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.services.AbstractService;
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
import java.util.stream.Collectors;

@Service
public class JsoupServiceImpl extends AbstractService implements JsoupService {

    @Override
    public Flux<BillStatus> processHtmlRequest(String html) {
        Document document = Jsoup.parse(html);
        Element tableElement = findNavTab1Element(document, Element::firstElementChild);
        Element tbody = findTBodyElement(tableElement, Elements::first);
        Elements trElements = findTrElements(tbody);
        List<BillStatus> result = findTableElements(trElements);
        return Flux.fromIterable(result);

    }

    @Override
    public Flux<Bill> processSearchResult(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = findEuroBoxElements(document);
        List<Bill> bills = findBills(elements);
        return Flux.fromIterable(bills);
    }

    private List<Bill> findBills(Elements elements) {
        return elements.stream() //
                .map(element -> {
                    Elements tdElements = findTDElementsOfEuroBoxTable(element);
                    return buildBill(tdElements);
                }) //
                .collect(Collectors.toList());
    }

    private Elements findTDElementsOfEuroBoxTable(Element element) {
        return Optional.ofNullable(element.parent()) //
                .map(el -> el.getElementsByTag(TD_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT + "." + TD_ELEMENT));
    }


    private Bill buildBill(Elements tdElements) {
        return Bill.builder() //
                .link(tdElements.select(A_ELEMENT).first().attr(HREF_ELEMENT)) //
                .number(tdElements.get(1).text()) //
                .registrationDate(tdElements.get(2).text()) //
                .name(tdElements.last().text()) //
                .build();
    }

    private Elements findEuroBoxElements(Document document) {
        Elements elements = document.getElementsByClass(EURO_BOX_ELEMENT);
        if (elements.isEmpty()) {
            throw new JsoupServiceException(String.format(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT));
        }
        return elements;
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

}
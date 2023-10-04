package com.malex.services.impl;

import com.malex.exceptions.JsoupServiceException;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.services.AbstractService;
import com.malex.services.JsoupService;
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
    public Flux<BillStatus> processBillStatus(String html) {
        Document document = processHtml(html);
        Element tableElement = parseNavTab1Element(document, Element::firstElementChild);
        Element tbody = parseTBodyElement(tableElement, Elements::first);
        Elements trElements = parseTrElements(tbody);
        return toFlux(parseTdTableElements(trElements));
    }


    @Override
    public Flux<Bill> processBillSearchResult(String html) {
        Document document = processHtml(html);
        Elements elements = parseEuroBoxElements(document);
        return toFlux(parseTableElements(elements));
    }


    private List<Bill> parseTableElements(Elements elements) {
        return elements.stream() //
                .map(el -> buildBill(parseTdEuroBoxTableElements(el))) //
                .collect(Collectors.toList());
    }


    private Elements parseTdEuroBoxTableElements(Element element) {
        return Optional.ofNullable(element.parent()) //
                .map(el -> el.getElementsByTag(TD_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT + "." + TD_ELEMENT));
    }


    private Elements parseEuroBoxElements(Document document) {
        return Optional.of(document.getElementsByClass(EURO_BOX_ELEMENT)) //
                .filter(IS_ELEMENT_NOT_EMPTY) //
                .orElseThrow(() -> //
                        new JsoupServiceException(String.format(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT)));
    }


    private Element parseNavTab1Element(Element element, Function<Element, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementById(NAV_TAB1_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, NAV_TAB1_ELEMENT));
    }


    private Element parseTBodyElement(Element element, Function<Elements, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TBODY_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TBODY_ELEMENT));
    }


    private Elements parseTrElements(Element element) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TR_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, TR_ELEMENT));
    }


    private List<BillStatus> parseTdTableElements(Elements elements) {
        return elements.stream() //
                .map(els -> Optional.of(els.getElementsByTag(TD_ELEMENT)) //
                        .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TD_ELEMENT))) //
                .map(this::buildModel) //
                .collect(Collectors.toList());
    }

}
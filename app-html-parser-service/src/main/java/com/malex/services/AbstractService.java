package com.malex.services;

import com.malex.exceptions.JsoupServiceException;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractService {

    /**
     * Tags or html classes constants
     */
    protected final static String NAV_TAB1_ELEMENT = "nav-tab1";
    protected final static String TBODY_ELEMENT = "tbody";
    protected final static String TR_ELEMENT = "tr";
    protected final static String TD_ELEMENT = "td";
    protected final static String A_ELEMENT = "a";

    protected final static String EURO_BOX_ELEMENT = "euro-box";
    protected final static String HREF_ELEMENT = "href";

    /**
     * Error messages
     */
    protected final static String ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND = "Element by tag - '%s' was not found";
    protected final static String ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND = "Elements by tag - '%s' was not found";

    protected static final Predicate<Elements> IS_ELEMENT_NOT_EMPTY = el -> !el.isEmpty();

    protected Document processHtml(String html) {
        return Jsoup.parse(html);
    }

    protected <T> Flux<T> toFlux(Iterable<? extends T> it) {
        return Flux.fromIterable(it);
    }


    protected Supplier<JsoupServiceException> buildError(String errorMessage, String tag) {
        return () -> new JsoupServiceException(String.format(errorMessage, tag));
    }

    protected BillStatus buildModel(Elements tdElements) {
        return BillStatus.builder() //
                .data(getElementText(tdElements, "first -> data", Elements::first)) //
                .status(getElementText(tdElements, "last -> status", Elements::last)) //
                .build();
    }

    private String getElementText(Elements elements, String tag, Function<Elements, Element> fn) {
        Element element = fn.apply(elements);
        return Optional.ofNullable(element) //
                .map(Element::text) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, tag));

    }

    protected Bill buildBill(Elements tdElements) {
        return Bill.builder() //
                .link(parseBillLink(tdElements)) //
                .number(parseBillNumber(tdElements)) //
                .registrationDate(parseBillRegistrationDate(tdElements)) //
                .name(parseBillName(tdElements)) //
                .build();
    }

    private String parseBillName(Elements tdElements) {
        return Optional.ofNullable(tdElements.last()) //
                .map(Element::text) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, "td, for bill name"));
    }

    private String parseBillRegistrationDate(Elements tdElements) {
        return tdElements.get(2).text();
    }

    private String parseBillNumber(Elements tdElements) {
        return tdElements.get(1).text();
    }

    private String parseBillLink(Elements tdElements) {
        return Optional.of(tdElements.select(A_ELEMENT)) //
                .map(Elements::first) //
                .map(el -> el.attr(HREF_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, HREF_ELEMENT + ", for bill link"));
    }

}

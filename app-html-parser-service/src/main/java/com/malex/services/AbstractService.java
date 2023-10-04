package com.malex.services;

import com.malex.exceptions.JsoupServiceException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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


    protected Supplier<JsoupServiceException> buildError(String errorMessage, String tag) {
        return () -> new JsoupServiceException(String.format(errorMessage, tag));
    }


    protected List<Elements> parseTableElements(Elements elements) {
        return elements.stream() //
                .map(this::parseTdEuroBoxTableElements) //
                .collect(Collectors.toList());
    }


    private Elements parseTdEuroBoxTableElements(Element element) {
        return Optional.ofNullable(element.parent()) //
                .map(el -> el.getElementsByTag(TD_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT + "." + TD_ELEMENT));
    }

    protected List<Elements> parseTdTableElements(Elements elements) {
        return elements.stream() //
                .map(els -> Optional.of(els.getElementsByTag(TD_ELEMENT)) //
                        .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TD_ELEMENT))) //
                .collect(Collectors.toList());
    }


    protected Elements parseEuroBoxElements(Document document) {
        return Optional.of(document.getElementsByClass(EURO_BOX_ELEMENT)) //
                .filter(IS_ELEMENT_NOT_EMPTY) //
                .orElseThrow(() -> //
                        new JsoupServiceException(String.format(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, EURO_BOX_ELEMENT)));
    }


    protected Element parseNavTab1Element(Element element, Function<Element, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementById(NAV_TAB1_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, NAV_TAB1_ELEMENT));
    }


    protected Element parseTBodyElement(Element element, Function<Elements, Element> applyFunction) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TBODY_ELEMENT)) //
                .map(applyFunction) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENT_BY_TAG_NOT_FOUND, TBODY_ELEMENT));
    }


    protected Elements parseTrElements(Element element) {
        return Optional.ofNullable(element) //
                .map(el -> el.getElementsByTag(TR_ELEMENT)) //
                .orElseThrow(buildError(ERROR_MSG_ELEMENTS_BY_TAG_NOT_FOUND, TR_ELEMENT));
    }


}

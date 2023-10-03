package com.malex.services;

import com.malex.exceptions.JsoupServiceException;

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


    protected Supplier<JsoupServiceException> buildError(String errorMessage, String tag) {
        return () -> new JsoupServiceException(String.format(errorMessage, tag));
    }
}

package com.malexj.model.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proxy Json request representation
 * @param url - Url to site
 */
public record ProxyRequest(@JsonProperty("url") String url) {}

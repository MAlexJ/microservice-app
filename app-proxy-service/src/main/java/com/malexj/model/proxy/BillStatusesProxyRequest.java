package com.malexj.model.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proxy Json request representation
 *
 * @param url - Url to site
 */
public record BillStatusesProxyRequest(@JsonProperty("url") String url) {}

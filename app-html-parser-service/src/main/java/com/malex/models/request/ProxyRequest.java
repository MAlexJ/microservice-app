package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProxyRequest {

    @JsonProperty("link")
    private String link;
}

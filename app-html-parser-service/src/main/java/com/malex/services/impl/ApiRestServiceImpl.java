package com.malex.services.impl;

import com.malex.models.base.FormUrlencodedData;
import com.malex.services.AbstractRestService;
import com.malex.services.ApiRestService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRestServiceImpl extends AbstractRestService implements ApiRestService {

  @Value("${html.bill-search-url}")
  private String searchBillUrl;

  private final WebClient webClient;

  @Override
  public Mono<String> fetchBillStatus(String url) {
    return webClient.get().uri(url).retrieve().bodyToMono(String.class);
  }

  @Override
  public Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData) {
    return webClient
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(convertFromDataToMultiValueMap(formData)))
        .retrieve()
        .bodyToMono(String.class);
  }

  @Override
  public Mono<String> fetchSearchResult(MultiValueMap<String, String> formData) {
    return webClient
        .post()
        .uri(searchBillUrl)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(formData))
        .retrieve()
        .bodyToMono(String.class);
  }

  private MultiValueMap<String, String> convertFromDataToMultiValueMap(
      List<FormUrlencodedData> data) {
    Map<String, List<String>> formUrlencodedData = formDataToMap(data);
    return new LinkedMultiValueMap<>(formUrlencodedData);
  }

  private Map<String, List<String>> formDataToMap(List<FormUrlencodedData> data) {
    return data.stream()
        .collect(
            Collectors.toMap(FormUrlencodedData::getKey, formData -> List.of(formData.getValue())));
  }
}

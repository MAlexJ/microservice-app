package com.malexj.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.BillStatus;
import com.malexj.service.NotificationService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${mail-service.url}")
    private String mailServiceUrl;

    @Value("${mail-service.path.send-email}")
    private String pathSendMail;

    private final WebClient webClient;

    @Override
    public Mono<Void> sendNotification(Set<BillStatus> statuses) {
        log.info(">>> Send diff to notification service");
        URI uri = UriComponentsBuilder.fromUriString(mailServiceUrl) //
                .path(pathSendMail) //
                .build() //
                .toUri();

        EmailRequest emailRequest = EmailRequest.builder().toEmail("XxXxx@gmail.com").message("Diff statuses: " + statuses).title("Change bill status").build();

        WebClient.ResponseSpec retrieve = webClient.post() //
                .uri(uri).contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(emailRequest) //
                .retrieve();

        retrieve.toBodilessEntity() //
                .doOnNext(voidResponseEntity -> log.info(voidResponseEntity.toString()));

        return Mono.empty();
    }

    @Data
    @Builder
    public static class EmailRequest {

        @JsonProperty("toEmail")
        private String toEmail;

        @JsonProperty("title")
        private String title;

        @JsonProperty("message")
        private String message;
    }

}

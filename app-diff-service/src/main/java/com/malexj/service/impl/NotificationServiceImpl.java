package com.malexj.service.impl;

import com.malexj.model.BillStatus;
import com.malexj.model.request.EmailRequest;
import com.malexj.service.NotificationService;
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
        EmailRequest emailRequest = buildNotificationMessage(statuses);

        return webClient.post() //
                .uri(builduri()) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(emailRequest) //
                .retrieve() //
                .bodyToMono(Void.class);
    }

    private static EmailRequest buildNotificationMessage(Set<BillStatus> statuses) {
        return EmailRequest.builder() //
                .toEmail("xxx") //
                .title("The status of '9672' bill has changed to : " + statuses) //
                .message("Change bill status") //
                .build();
    }

    private URI builduri() {
        return UriComponentsBuilder.fromUriString(mailServiceUrl) //
                .path(pathSendMail) //
                .build() //
                .toUri();
    }

}
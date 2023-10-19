package com.malexj.service.impl;

import com.malexj.model.User;
import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.EmailRequest;
import com.malexj.service.AbstractService;
import com.malexj.service.NotificationService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl extends AbstractService implements NotificationService {

    @Value("${mail-service.application.name}")
    private String virtualHostname;

    @Value("${mail-service.endpoint.send-email}")
    private String endpoint;

    @Value("${mail-service.template.title}")
    private String titleTemplate;

    @Value("${mail-service.template.message}")
    private String messageTemplate;

    // TODO temporary solution until the subscriber service is implemented
    @Value("${mail-service.test.default-recipient}")
    private String defaultRecipient;

    @Lazy
    private final EurekaClient eurekaClient;

    private final WebClient webClient;


    private String discoveryServiceUrl() {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(virtualHostname, false);
        return nextServerFromEureka.getHomePageUrl();
    }

    @Override
    public Mono<BillDiffRequest> sendNotification(BillDiffRequest request) {
        log.info("Send diff to notification service");
        return webClient.post() //
                .uri(buildUri(discoveryServiceUrl(), endpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildNotificationRequest(request)) //
                .retrieve() //
                .bodyToMono(Void.class) //
                .then(Mono.just(request));
    }

    private EmailRequest buildNotificationRequest(BillDiffRequest request) {
        // TODO temporary solution until the subscriber service is implemented
        String recipientEmail = Optional.ofNullable(request.getUser()) //
                .map(User::getEmail) //
                .orElse(defaultRecipient);
        return EmailRequest.builder() //
                .toEmail(recipientEmail) //
                .title(fetchTitle(request.getNumber())) //
                .message(fetchMessage(request)) //
                .build();
    }

    private String fetchTitle(String number) {
        return String.format(titleTemplate, number);
    }

    private String fetchMessage(BillDiffRequest request) {
        return String.format(messageTemplate, request.getNumber(), request.getName(), request.getDiffStatuses());
    }

}
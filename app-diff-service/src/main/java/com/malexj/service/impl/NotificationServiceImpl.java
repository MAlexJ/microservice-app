package com.malexj.service.impl;

import com.malexj.model.User;
import com.malexj.model.request.BillDiffRequest;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${mail-service.url}")
    private String mailServiceUrl;

    @Value("${mail-service.path.send-email}")
    private String pathSendMail;

    // TODO temporary solution until the subscriber service is implemented
    @Value("${mail-service.test.default-recipient}")
    private String defaultRecipient;

    private final static String MESSAGE_TEMPLATE = "Statuses in bill - '%s' have changed, \n bill name: %s \n new status - %s";
    private final static String TITLE_TEMPLATE = "The status of %s bill has changed";

    private final WebClient webClient;

    @Override
    public Mono<Void> sendNotification(BillDiffRequest request) {
        log.info(">>> Send diff to notification service");
        return webClient.post() //
                .uri(builduri()) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildNotificationRequest(request)) //
                .retrieve() //
                .bodyToMono(Void.class);
    }

    private EmailRequest buildNotificationRequest(BillDiffRequest request) {
        // TODO temporary solution until the subscriber service is implemented
        String recipientEmail = Optional.ofNullable(request.getUser()) //
                .map(User::getEmail) //
                .orElse(defaultRecipient);

        return EmailRequest.builder() //
                .toEmail(recipientEmail) //
                .title(fetchMessage(request)) //
                .message(fetchTitle(request.getNumber())) //
                .build();
    }

    private String fetchTitle(String number) {
        return String.format(TITLE_TEMPLATE, number);
    }

    private String fetchMessage(BillDiffRequest request) {
        return String.format(MESSAGE_TEMPLATE, request.getName(), request.getName(), request.getDiffStatuses());
    }

    private URI builduri() {
        return UriComponentsBuilder.fromUriString(mailServiceUrl) //
                .path(pathSendMail) //
                .build() //
                .toUri();
    }

}
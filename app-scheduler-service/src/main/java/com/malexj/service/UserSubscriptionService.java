package com.malexj.service;

import com.malexj.model.request.UserSubscriptionRequest;
import reactor.core.publisher.Flux;

public interface UserSubscriptionService {

    Flux<UserSubscriptionRequest> fetchUserSubscriptions();
}

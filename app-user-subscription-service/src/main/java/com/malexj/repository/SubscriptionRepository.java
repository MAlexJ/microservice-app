package com.malexj.repository;

import com.malexj.model.entity.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, String> {
}

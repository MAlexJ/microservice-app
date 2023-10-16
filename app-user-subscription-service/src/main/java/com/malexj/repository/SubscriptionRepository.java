package com.malexj.repository;

import com.malexj.model.entity.SubscriptionEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<SubscriptionEntity, String> {


    @Query(value = "{ isActive : ?0 }")
    Flux<SubscriptionEntity> findActiveSubscription(boolean isActive);

    Mono<SubscriptionEntity> findSubscriptionEntitiesByUser_Email(String email);

    @Query("{'user.email': ?0 }")
    @Update(update = "{ $set: { isActive : false }}")
    Mono<Long> updateSubscription(String email);
}

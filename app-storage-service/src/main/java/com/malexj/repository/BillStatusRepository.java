package com.malexj.repository;

import com.malexj.entity.BillStatus;
import com.malexj.entity.QBillStatus;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillStatusRepository extends CrudRepository<BillStatus, Long>,  //
        QuerydslPredicateExecutor<BillStatus>, //
        QuerydslBinderCustomizer<QBillStatus> {

    @Override
    default void customize(QuerydslBindings bindings, QBillStatus status) {
    }
}

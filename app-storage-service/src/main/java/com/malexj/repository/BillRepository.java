package com.malexj.repository;

import com.malexj.entity.Bill;
import com.malexj.entity.QBill;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillRepository extends CrudRepository<Bill, Long>,  //
        QuerydslPredicateExecutor<Bill>, //
        QuerydslBinderCustomizer<QBill> {

    @Override
    default void customize(QuerydslBindings bindings, QBill bill) {
    }
}

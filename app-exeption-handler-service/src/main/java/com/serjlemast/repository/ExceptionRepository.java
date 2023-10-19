package com.serjlemast.repository;

import com.serjlemast.entity.ExceptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ExceptionRepository extends CrudRepository<ExceptionEntity, Long> {

    List<ExceptionEntity> findAll();

}

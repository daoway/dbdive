package com.blogspot.ostas.apps.dbdive.jpa.repository;

import com.blogspot.ostas.apps.dbdive.jpa.domain.AppAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAuthRepository extends CrudRepository<AppAuth, String> {

}

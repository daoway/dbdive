package com.blogspot.ostas.apps.dbdive.jpa.repository;

import com.blogspot.ostas.apps.dbdive.jpa.domain.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, String> {

}

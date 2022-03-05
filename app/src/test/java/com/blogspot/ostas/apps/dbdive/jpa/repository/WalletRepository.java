package com.blogspot.ostas.apps.dbdive.jpa.repository;

import com.blogspot.ostas.apps.dbdive.jpa.domain.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, String> {

}

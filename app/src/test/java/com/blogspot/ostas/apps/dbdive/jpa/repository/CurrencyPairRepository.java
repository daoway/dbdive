package com.blogspot.ostas.apps.dbdive.jpa.repository;

import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.CurrencyPair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends CrudRepository<CurrencyPair, String> {

}

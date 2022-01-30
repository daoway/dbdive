package com.blogspot.ostas.apps.dbdive.jpa.repository;

import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {

}

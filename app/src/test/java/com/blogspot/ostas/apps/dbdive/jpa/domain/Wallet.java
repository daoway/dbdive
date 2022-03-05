package com.blogspot.ostas.apps.dbdive.jpa.domain;

import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.Currency;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.math.BigDecimal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Entity
public class Wallet extends Identifiable {

	@ElementCollection
	private Map<Currency, BigDecimal> moneyMap = new ConcurrentHashMap<>();

}

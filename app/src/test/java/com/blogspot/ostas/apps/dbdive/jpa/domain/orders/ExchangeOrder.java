package com.blogspot.ostas.apps.dbdive.jpa.domain.orders;

import com.blogspot.ostas.apps.dbdive.jpa.domain.Identifiable;
import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.CurrencyPair;
import com.blogspot.ostas.apps.dbdive.jpa.domain.enums.OrderSide;
import lombok.Data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type", discriminatorType = DiscriminatorType.STRING)
public class ExchangeOrder extends Identifiable {

	@Enumerated
	private OrderSide type;

	@ManyToOne
	@JoinColumn(name = "pair_id")
	private CurrencyPair pair;

}

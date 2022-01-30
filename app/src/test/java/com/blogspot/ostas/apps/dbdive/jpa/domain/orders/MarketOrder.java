package com.blogspot.ostas.apps.dbdive.jpa.domain.orders;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@DiscriminatorValue("MARKET")
public class MarketOrder extends ExchangeOrder {

	private BigDecimal moneyAmount;

}

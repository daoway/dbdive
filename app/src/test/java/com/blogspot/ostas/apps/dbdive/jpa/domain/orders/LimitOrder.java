package com.blogspot.ostas.apps.dbdive.jpa.domain.orders;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@DiscriminatorValue("LIMIT")
public class LimitOrder extends ExchangeOrder {

	private BigDecimal amount;

	private BigDecimal price;

	private BigDecimal amountCompleted;

	private boolean isCompleted;

}

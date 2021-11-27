package com.blogspot.ostas.apps.dbdive.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetails {

	private int orderNumber;

	private String productCode;

	private int quantityOrdered;

	private BigDecimal priceEach;

	private short orderLineNumber;

}

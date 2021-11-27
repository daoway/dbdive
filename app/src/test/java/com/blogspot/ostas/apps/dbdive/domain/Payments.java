package com.blogspot.ostas.apps.dbdive.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Payments {

	private int customerNumber;

	private String checkNumber;

	private Date paymentDate;

	private BigDecimal amount;

}

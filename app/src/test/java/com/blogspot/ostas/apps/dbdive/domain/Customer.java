package com.blogspot.ostas.apps.dbdive.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Customer {

	private Integer customerNumber;

	private String customerName;

	private String contactLastName;

	private String contactFirstName;

	private String phone;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String postalCode;

	private String country;

	private Integer salesRepEmployeeNumber;

	private BigDecimal creditLimit;

}

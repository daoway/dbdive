package com.blogspot.ostas.apps.dbdive.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {

	private int orderNumber;

	private Date orderDate;

	private Date requiredDate;

	private Date shippedDate;

	private String status;

	private String comments;

	private int customerNumber;

}

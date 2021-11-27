package com.blogspot.ostas.apps.dbdive.domain;

import lombok.Data;

@Data
public class Employee {

	private int employeeNumber;

	private String lastName;

	private String firstName;

	private String extension;

	private String email;

	private String officeCode;

	private int reportsTo;

	private String jobTitle;

}

package com.blogspot.ostas.apps.dbdive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class DbColumn {

	private String name;

	private String type;

	private String size;

}

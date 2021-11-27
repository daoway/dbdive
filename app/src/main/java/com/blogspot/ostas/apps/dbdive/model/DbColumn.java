package com.blogspot.ostas.apps.dbdive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DbColumn {

	private String name;

	private String type;

	private String size;

}

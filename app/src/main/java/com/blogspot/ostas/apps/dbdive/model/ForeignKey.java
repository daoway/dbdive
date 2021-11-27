package com.blogspot.ostas.apps.dbdive.model;

import lombok.Data;

@Data
public class ForeignKey {

	private String fkTableName;

	private String fkColumn;

	private String pkTableName;

	private String pkColumn;

	private String fkSchema;

}

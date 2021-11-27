package com.blogspot.ostas.apps.dbdive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DbForeignKey {

	private String fkTableName;

	private String fkColumn;

	private String pkTableName;

	private String pkColumn;

	private String fkSchema;

}

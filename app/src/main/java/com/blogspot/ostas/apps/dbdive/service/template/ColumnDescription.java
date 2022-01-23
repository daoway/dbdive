package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import lombok.Data;

@Data
public class ColumnDescription {

	private String name;

	private String javaType;

	public ColumnDescription(DbColumn dbColumn) {
		this.name = dbColumn.getName();
		this.javaType = dbColumn.getJavaType().getSimpleName();
	}

}

package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import lombok.Data;

import static com.blogspot.ostas.apps.dbdive.utils.StringUtils.underscoreToCamelCase;

@Data
public class ColumnDescription {

	private String name;

	private String javaType;

	public ColumnDescription(DbColumn dbColumn) {
		this.name = underscoreToCamelCase(dbColumn.getName());
		this.javaType = dbColumn.getJavaType().getSimpleName();
	}

}

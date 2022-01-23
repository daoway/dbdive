package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.google.common.base.CaseFormat;
import lombok.Data;

import java.util.Locale;

@Data
public class ColumnDescription {

	private String name;

	private String javaType;

	public ColumnDescription(DbColumn dbColumn) {
		this.name = toCamelCase(dbColumn.getName().toLowerCase(Locale.ROOT));
		this.javaType = dbColumn.getJavaType().getSimpleName();
	}

	public static String toCamelCase(String columnName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
	}

}

package com.blogspot.ostas.apps.dbdive.utils;

import com.google.common.base.CaseFormat;

import static org.springframework.util.StringUtils.capitalize;

public class StringUtils {

	public static String getClassNameFromTable(String s) {
		return capitalize(underscoreToCamelCase(s));
	}

	public static String toCamelCase(String columnName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
	}

	public static String underscoreToCamelCase(String str) {
		if (str.contains("_")) {
			return toCamelCase(str);
		}
		else {
			return str;
		}
	}

}

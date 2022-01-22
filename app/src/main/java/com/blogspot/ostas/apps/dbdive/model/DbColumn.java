package com.blogspot.ostas.apps.dbdive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DbColumn {

	private String name;

	private String type;

	private Class<?> javaType;

	private String size;

}

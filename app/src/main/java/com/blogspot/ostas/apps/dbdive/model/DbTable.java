package com.blogspot.ostas.apps.dbdive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class DbTable {

	private String name;

	private List<DbColumn> columns;

	private List<PrimaryKey> primaryKeys;

	private List<ForeignKey> exportedForeignKeys;

	private List<ForeignKey> importedForeignKeys;

}

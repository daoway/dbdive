package com.blogspot.ostas.apps.dbdive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class DbTable {

	private String name;

	private List<DbColumn> columns;

	private List<DbPrimaryKey> primaryKeys;

	private List<DbForeignKey> exportedForeignKeys;

	private List<DbForeignKey> importedForeignKeys;

	@Override
	public String toString() {
		return this.name;
	}

}

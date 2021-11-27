package com.blogspot.ostas.apps.dbdive.utils;

import com.blogspot.ostas.apps.dbdive.model.DbTable;
import com.blogspot.ostas.apps.dbdive.model.DbTableColumn;
import com.blogspot.ostas.apps.dbdive.model.ForeignKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResultSetHelper {

	@SneakyThrows
	public static DbTable extractDbTable(ResultSet resultSet) {
		var name = resultSet.getString("TABLE_NAME");
		var table = new DbTable();
		table.setName(name);
		return table;
	}

	@SneakyThrows
	public static ForeignKey extractForeignKeysInfo(ResultSet keysResultSet) {
		var foreignKey = new ForeignKey();
		foreignKey.setFkTableName(keysResultSet.getString("FKTABLE_NAME"));
		foreignKey.setFkColumn(keysResultSet.getString("FKCOLUMN_NAME"));

		foreignKey.setPkTableName(keysResultSet.getString("PKTABLE_NAME"));
		foreignKey.setPkColumn(keysResultSet.getString("PKCOLUMN_NAME"));

		foreignKey.setFkSchema(keysResultSet.getString("FKTABLE_SCHEM"));

		return foreignKey;
	}

	@SneakyThrows
	public static DbTableColumn extractColumnInfo(ResultSet resultSet) {
		var name = resultSet.getString("COLUMN_NAME");
		var type = resultSet.getString("TYPE_NAME");
		var size = resultSet.getString("COLUMN_SIZE");
		return new DbTableColumn(name, type, size);
	}

}

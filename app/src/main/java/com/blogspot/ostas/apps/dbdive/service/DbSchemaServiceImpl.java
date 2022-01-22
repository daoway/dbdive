package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.model.DbForeignKey;
import com.blogspot.ostas.apps.dbdive.model.DbPrimaryKey;
import com.blogspot.ostas.apps.dbdive.model.DbSchema;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.blogspot.ostas.apps.dbdive.utils.ResultSetHelper.extractColumnInfo;
import static com.blogspot.ostas.apps.dbdive.utils.ResultSetHelper.extractDbTable;
import static com.blogspot.ostas.apps.dbdive.utils.ResultSetHelper.extractForeignKeysInfo;
import static org.springframework.jdbc.support.JdbcUtils.extractDatabaseMetaData;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbSchemaServiceImpl implements DbSchemaService {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	@SneakyThrows
	private Map<String, DbTable> getAllTables(String schemaName) {
		return extractDatabaseMetaData(this.dataSource, (databaseMetaData) -> {
			try (var resultSet = databaseMetaData.getTables(schemaName, "", null, new String[] { "TABLE" })) {
				var tables = new LinkedHashMap<String, DbTable>();
				while (resultSet.next()) {
					var dbTable = extractDbTable(resultSet);
					tables.put(dbTable.getName(), dbTable);
				}
				return tables;
			}
		});
	}

	@SneakyThrows
	private void setExportedForeignKeys(DbSchema dbSchema, String schemaName) {
		extractDatabaseMetaData(this.dataSource, (databaseMetaData) -> {
			dbSchema.getTables().keySet().forEach((tableName) -> {
				try (var resultSet = databaseMetaData.getExportedKeys(null, schemaName, tableName)) {
					var foreignKeys = extractForeignKeys(resultSet);
					if (!foreignKeys.isEmpty()) {
						dbSchema.getTables().get(tableName).setExportedForeignKeys(foreignKeys);
					}
				}
				catch (SQLException sqlException) {
					log.error("Error : ", sqlException);
				}
			});
			return null;
		});
	}

	@SneakyThrows
	private void setImportedForeignKeys(DbSchema dbSchema, String schemaName) {
		extractDatabaseMetaData(this.dataSource, (databaseMetaData) -> {
			dbSchema.getTables().keySet().forEach((tableName) -> {
				try (var resultSet = databaseMetaData.getImportedKeys(null, schemaName, tableName)) {
					var foreignKeys = extractForeignKeys(resultSet);
					if (!foreignKeys.isEmpty()) {
						dbSchema.getTables().get(tableName).setImportedForeignKeys(foreignKeys);
					}
				}
				catch (SQLException sqlException) {
					log.error("Error : ", sqlException);
				}
			});
			return null;
		});
	}

	@SneakyThrows
	private List<DbForeignKey> extractForeignKeys(ResultSet keysResultSet) {
		var foreignKeys = new ArrayList<DbForeignKey>();
		while (keysResultSet.next()) {
			var foreignKey = extractForeignKeysInfo(keysResultSet);
			foreignKeys.add(foreignKey);
		}
		return foreignKeys;
	}

	@Override
	public DbSchema getDbSchema(String schemaName) {
		var dbSchema = new DbSchema();
		var tables = getAllTables(schemaName);
		dbSchema.setTables(tables);
		setExportedForeignKeys(dbSchema, schemaName.toUpperCase());
		setImportedForeignKeys(dbSchema, schemaName.toUpperCase());
		setPrimaryKeys(dbSchema, schemaName);
		setColumns(dbSchema, schemaName);
		return dbSchema;
	}

	private void setColumns(DbSchema schema, String schemaNameAsString) {
		for (var dbTable : schema.getTables().values()) {
			setColumnsForTable(dbTable, schemaNameAsString);
		}
	}

	@SneakyThrows
	private void setColumnsForTable(DbTable table, String schema) {
		extractDatabaseMetaData(this.dataSource, (databaseMetaData) -> {
			try (var resultSet = databaseMetaData.getColumns(null, schema, table.getName(), null)) {
				table.setColumns(new ArrayList<>());
				while (resultSet.next()) {
					var dbColumn = extractColumnInfo(resultSet);
					setColumnJavaType(table, dbColumn);
					table.getColumns().add(dbColumn);
				}
			}
			return null;
		});
	}

	private void setColumnJavaType(DbTable table, DbColumn dbColumn) {
		this.jdbcTemplate.query(String.format("select %s from %s where 1=2", dbColumn.getName(), table.getName()),
				(rs) -> {
					var resultSetMetaData = rs.getMetaData();
					try {
						dbColumn.setJavaType(Class.forName(resultSetMetaData.getColumnClassName(1)));
					}
					catch (ClassNotFoundException exception) {
						log.error("Error casting class : ", exception);
					}
					return 0;
				});
	}

	private void setPrimaryKeys(DbSchema schema, String schemaName) {
		for (var dbTable : schema.getTables().values()) {
			setPrimaryKeyForTable(dbTable, schemaName);
		}
	}

	@SneakyThrows
	private void setPrimaryKeyForTable(DbTable table, String schema) {
		extractDatabaseMetaData(this.dataSource, (databaseMetaData) -> {
			try (var resultSet = databaseMetaData.getPrimaryKeys(null, schema, table.getName())) {
				table.setPrimaryKeys(new ArrayList<>());
				while (resultSet.next()) {
					var pkColumnName = resultSet.getString("COLUMN_NAME");
					table.getPrimaryKeys().add(new DbPrimaryKey(pkColumnName));
				}
			}
			return null;
		});
	}

}

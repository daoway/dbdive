package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbSchema;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.model.DbForeignKey;
import com.blogspot.ostas.apps.dbdive.model.DbPrimaryKey;
import com.blogspot.ostas.apps.dbdive.service.xml.DbColumnMixIn;
import com.blogspot.ostas.apps.dbdive.service.xml.DbSchemaMixIn;
import com.blogspot.ostas.apps.dbdive.service.xml.DbTableMixIn;
import com.blogspot.ostas.apps.dbdive.service.xml.ForeignKeyMixIn;
import com.blogspot.ostas.apps.dbdive.service.xml.PrimaryKeyMixIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlOperations {

	private static final ObjectMapper objectMapper;
	static {
		objectMapper = new XmlMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.addMixIn(DbSchema.class, DbSchemaMixIn.class);
		objectMapper.addMixIn(DbTable.class, DbTableMixIn.class);
		objectMapper.addMixIn(DbColumn.class, DbColumnMixIn.class);
		objectMapper.addMixIn(DbPrimaryKey.class, PrimaryKeyMixIn.class);
		objectMapper.addMixIn(DbForeignKey.class, ForeignKeyMixIn.class);
	}

	@SneakyThrows
	public static void exportSchemaAsXML(DbSchema dbSchema, String fileName) {
		objectMapper.writeValue(new File(fileName), dbSchema);
	}

	@SneakyThrows
	public static DbSchema deserializeFromXml(String fileName) {
		var contents = Files.readString(Path.of(fileName));
		return objectMapper.readValue(contents, DbSchema.class);
	}

}

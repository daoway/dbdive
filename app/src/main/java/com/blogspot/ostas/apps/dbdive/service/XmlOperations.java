package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbSchema;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlOperations {

	private static final XmlMapper xmlMapper;
	static {
		xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@SneakyThrows
	public static void exportSchemaAsXML(DbSchema dbSchema, String fileName) {
		xmlMapper.writeValue(new File(fileName), dbSchema);
	}

}

package com.blogspot.ostas.apps.dbdive.service.xml;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.model.DbForeignKey;
import com.blogspot.ostas.apps.dbdive.model.DbPrimaryKey;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DbTableMixIn {

	@JacksonXmlElementWrapper(localName = "columns")
	@JacksonXmlProperty(localName = "column")
	private List<DbColumn> columns;

	@JacksonXmlElementWrapper(localName = "primaryKeys")
	@JacksonXmlProperty(localName = "pk")
	private List<DbPrimaryKey> primaryKeys;

	@JacksonXmlElementWrapper(localName = "exportedForeignKeys")
	@JacksonXmlProperty(localName = "exportedForeignKey")
	private List<DbForeignKey> exportedForeignKeys;

	@JacksonXmlElementWrapper(localName = "importedForeignKeys")
	@JacksonXmlProperty(localName = "importedForeignKey")
	private List<DbForeignKey> importedForeignKeys;

}

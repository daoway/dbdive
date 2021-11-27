package com.blogspot.ostas.apps.dbdive.service.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ForeignKeyMixIn {

	@JacksonXmlProperty(isAttribute = true)
	private String fkTableName;

	@JacksonXmlProperty(isAttribute = true)
	private String fkColumn;

	@JacksonXmlProperty(isAttribute = true)
	private String pkTableName;

	@JacksonXmlProperty(isAttribute = true)
	private String pkColumn;

	@JacksonXmlProperty(isAttribute = true)
	private String fkSchema;

}

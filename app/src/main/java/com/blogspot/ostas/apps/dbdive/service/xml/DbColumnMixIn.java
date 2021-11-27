package com.blogspot.ostas.apps.dbdive.service.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class DbColumnMixIn {

	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String type;

	@JacksonXmlProperty(isAttribute = true)
	private String size;

}

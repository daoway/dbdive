package com.blogspot.ostas.apps.dbdive.service.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class PrimaryKeyMixIn {

	@JacksonXmlProperty(isAttribute = true)
	private String name;

}

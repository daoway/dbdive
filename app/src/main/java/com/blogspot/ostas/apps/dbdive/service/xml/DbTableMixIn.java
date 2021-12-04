package com.blogspot.ostas.apps.dbdive.service.xml;

import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DbTableMixIn {
    @JacksonXmlElementWrapper(localName = "columns")
    @JacksonXmlProperty(localName = "column")
    private List<DbColumn> columns;
}

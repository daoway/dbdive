package com.blogspot.ostas.apps.dbdive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode
public class DbSchema {

	private Map<String, DbTable> tables;

}

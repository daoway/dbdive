package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbSchema;

public interface JdbcMetadataService {

	DbSchema getDbSchema(String schemaName);

}

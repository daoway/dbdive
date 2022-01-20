package com.blogspot.ostas.apps.dbdive.service.template;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteInfo {

	private String table;

	private String column;

	private Object columnValue;

}

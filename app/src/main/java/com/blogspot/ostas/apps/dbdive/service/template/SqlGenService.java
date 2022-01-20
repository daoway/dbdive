package com.blogspot.ostas.apps.dbdive.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SqlGenService {

	private final TemplateService templateService;

	public String getDeleteSql(DeleteInfo deleteInfo) {
		var placeholders = new HashMap<String, Object>();
		placeholders.put("deleteInfo", deleteInfo);
		return this.templateService.populateTemplate("delete.vm", placeholders);
	}

}

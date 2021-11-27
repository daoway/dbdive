package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SqlGenService {
    private final TemplateService templateService;
    public String getDeleteSql(String tableName) {
        var placeholders = new HashMap<String, Object>();
        placeholders.put("table", tableName);
        return templateService.populateTemplate("delete.vm", placeholders);
    }
}

package com.blogspot.ostas.apps.dbdive.service.template;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateService {

	private final VelocityEngine velocityEngine;

	public String populateTemplate(String templateName, Map<String, Object> placeholders) {
		var template = this.velocityEngine.getTemplate(templateName);
		var context = new VelocityContext();
		placeholders.forEach(context::put);
		var templateResultWriter = new StringWriter();
		template.merge(context, templateResultWriter);
		return templateResultWriter.toString();
	}

}

package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.config.VelocityConfig;
import com.blogspot.ostas.apps.dbdive.service.SqlGenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD.UnusedPrivateField")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VelocityConfig.class)
public class SqlGenServiceTest {

	@SpyBean
	private SqlGenService sqlGenService;

	@SpyBean
	private TemplateService templateService;

	@Test
	public void deleteSql() {
		var deleteSql = sqlGenService.getDeleteSql("parent1");
		assertThat(deleteSql).isNotEmpty();
	}

}
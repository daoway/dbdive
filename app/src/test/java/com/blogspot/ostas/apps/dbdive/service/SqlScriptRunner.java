package com.blogspot.ostas.apps.dbdive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Component
@RequiredArgsConstructor
public class SqlScriptRunner {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	public void execSqlScript(String sql) {
		var populator = new ResourceDatabasePopulator();
		populator.setScripts(new ByteArrayResource(sql.getBytes()));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	public void execSqlScriptFromClasspathFile(String fileName) {
		var populator = new ResourceDatabasePopulator();
		populator.setScripts(new ClassPathResource(fileName));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	public Integer count(String table) {
		var sql = String.format("SELECT count(1) FROM %s", table);
		return singleResult(jdbcTemplate.query(sql, SingleColumnRowMapper.newInstance(Integer.class)));
	}

}

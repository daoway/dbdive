package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.MySqlContainerTests;
import com.blogspot.ostas.apps.dbdive.domain.Customer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.io.File;

import static com.blogspot.ostas.apps.dbdive.service.GraphOperations.exportAsGraphML;
import static com.blogspot.ostas.apps.dbdive.service.XmlOperations.exportSchemaAsXML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@Slf4j
@Sql(value = { "/mysqlsampledatabase.sql" }, config = @SqlConfig(transactionMode = ISOLATED),
		executionPhase = BEFORE_TEST_METHOD)
public class DbSchemaServiceOnMySqlTest implements MySqlContainerTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DbSchemaService dbSchemaService;

	@Test
	void schemaExistsAndHealthy() {
		var sql = "SELECT * FROM customers";
		var customers = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
		assertThat(customers).isNotEmpty();
	}

	@Test
	public void listAllTables() {
		var expected = new String[] { "customers", "employees", "offices", "orderdetails", "orders", "payments",
				"productlines", "products" };
		var actual = dbSchemaService.getDbSchema(DATABASE_NAME).getTables().keySet();
		assertThat(actual).containsExactlyInAnyOrder(expected);
	}

	@Test
	public void exportGraphML() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var graph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var exportedFile = new File("classic.graphml");
		exportAsGraphML(graph, exportedFile.getName());
		assertThat(exportedFile.exists()).isTrue();
	}

	@Test
	@SneakyThrows
	public void exportXml() {
		var exportedFile = new File("classic.xml");
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		exportSchemaAsXML(dbSchema, exportedFile.getName());
		assertThat(exportedFile.exists()).isTrue();
	}

}

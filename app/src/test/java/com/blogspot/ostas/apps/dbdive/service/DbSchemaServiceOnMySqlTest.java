package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.MySqlContainerTests;
import com.blogspot.ostas.apps.dbdive.domain.Customer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.io.File;

import static com.blogspot.ostas.apps.dbdive.service.GraphOperations.exportAsGraphML;
import static com.blogspot.ostas.apps.dbdive.service.XmlOperations.deserializeFromXml;
import static com.blogspot.ostas.apps.dbdive.service.XmlOperations.exportSchemaAsXML;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DbSchemaServiceOnMySqlTest implements MySqlContainerTests {

	@Autowired
	public DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DbSchemaService dbSchemaService;

	@Autowired
	private SqlScriptRunner scriptRunner;

	@BeforeAll
	void beforeAll() {
		scriptRunner.execSqlScriptFromClasspathFile("/mysqlsampledatabase.sql");
	}

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
	public void exportGraphAsPNG() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var graph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var exportedFile = new File("classic.png");
		GraphOperations.exportAsPng(graph, exportedFile.getName());
		assertThat(exportedFile.exists()).isTrue();
	}

	@Test
	@SneakyThrows
	public void serializeDbSchemaToXml() {
		var exportedFile = new File("classic.xml");
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		exportSchemaAsXML(dbSchema, exportedFile.getName());
		assertThat(exportedFile.exists()).isTrue();
	}

	@Test
	public void deserializeDbSchemaFromXml() {
		var exportedFile = new File("classic.xml");
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		exportSchemaAsXML(dbSchema, exportedFile.getName());
		var deserializedSchema = deserializeFromXml(exportedFile.getName());
		assertThat(deserializedSchema).isEqualTo(dbSchema);
	}

	@Test
	public void findCycle() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var graph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var cycles = GraphOperations.cycles(graph);
		final var employees = cycles.iterator().next();
		assertThat(employees.getName()).isEqualTo("employees");
	}

}

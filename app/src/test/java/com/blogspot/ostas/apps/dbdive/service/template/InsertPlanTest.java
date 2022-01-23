package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.MySqlContainerTests;
import com.blogspot.ostas.apps.dbdive.model.DbColumn;
import com.blogspot.ostas.apps.dbdive.service.DbSchemaService;
import com.blogspot.ostas.apps.dbdive.service.GraphOperations;
import com.blogspot.ostas.apps.dbdive.service.SqlScriptRunner;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.forName;

@Slf4j
@SpringBootTest
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InsertPlanTest implements MySqlContainerTests {

	@Autowired
	private SqlScriptRunner scriptRunner;

	@Autowired
	private DbSchemaService dbSchemaService;

	@BeforeAll
	void beforeAll() {
		scriptRunner.execSqlScriptFromClasspathFile("/sql/one-parent-many-children.sql");
	}

	@AfterAll
	void afterAll() {
		scriptRunner.execSqlScriptFromClasspathFile("/sql/one-parent-many-children-cleanup.sql");
	}

	@Test
	void getInsert() {
		var insertPlan = new InsertPlan();
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var dbGraph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var insertSequence = GraphOperations.removalSequence(dbGraph);
		Collections.reverse(insertSequence);
		// todo
		//		var insertByColumnName = "parent1_id";
		//		var insertedValue = "300";
		insertSequence.forEach(dbTable -> {
			var columns = dbTable.getColumns();
			var comaSeparatedColumns = columns.stream().map(DbColumn::getName).collect(Collectors.joining(", "));
			var rawRandomValues = columns.stream()
					.map(dbColumn -> randomColumnValueAsString(dbColumn))
					.collect(Collectors.joining(", "));
			var insertSql = String.format("insert into %s (%s) values (%s)", dbTable.getName(), comaSeparatedColumns,
					rawRandomValues);
			log.info(insertSql);
		});
	}
	EasyRandomParameters parameters = new EasyRandomParameters()
			.seed(123L)
			.objectPoolSize(100)
			.randomizationDepth(3)
			.charset(StandardCharsets.UTF_8)
			.timeRange(LocalTime.MIDNIGHT, LocalTime.NOON)
			.dateRange(LocalDate.now(), LocalDate.now().plusDays(5))
			.stringLengthRange(5, 50)
			.collectionSizeRange(1, 10)
			.scanClasspathForConcreteTypes(true)
			.overrideDefaultInitialization(false)
			.ignoreRandomizationErrors(true);
	EasyRandom generator = new EasyRandom(parameters);

	private String randomColumnValueAsString(DbColumn dbColumn) {
		return generator.nextObject(dbColumn.getJavaType()).toString();
	}

}
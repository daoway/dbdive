package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.MySqlContainerTests;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.dao.support.DataAccessUtils.singleResult;

@SpringBootTest
@DirtiesContext
@Sql(scripts = { "/sql/one-parent-many-children.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
		config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = "/sql/one-parent-many-children-cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GraphOperationsParentChildTest implements MySqlContainerTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DbSchemaService dbSchemaService;

	@Test
	void schemaExistsAndHealthy() {
		var sql = "SELECT parent1_id FROM parent1";
		var parent = jdbcTemplate.queryForObject(sql, Integer.class);
		assertThat(parent).isEqualTo(100500);
	}

	@Test
	void parentRemoval() {
		// given
		var deleteSequence = List.of("DELETE FROM child10 WHERE child10_id=100500",
				"DELETE FROM child9 WHERE child9_id=100500", "DELETE FROM child8 WHERE child8_id=100500",
				"DELETE FROM child7 WHERE child7_id=100500", "DELETE FROM child6 WHERE child6_id=100500",
				"DELETE FROM child5 WHERE child5_id=100500", "DELETE FROM child4 WHERE child4_id=100500",
				"DELETE FROM child3 WHERE child3_id=100500", "DELETE FROM child2 WHERE child2_id=100500",
				"DELETE FROM child1 WHERE child1_id=100500",

				"DELETE FROM parent1 WHERE parent1_id=100500");
		// when
		deleteSequence.forEach(deleteSql -> jdbcTemplate.execute(deleteSql));
		// then
		var sql = "SELECT parent1_id FROM parent1";
		var id = singleResult(jdbcTemplate.query(sql, SingleColumnRowMapper.newInstance(Integer.class)));
		assertThat(id).isNull();
	}

	@Test
	public void computeFarthestVertex() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var dbGraph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var youngestChild = GraphOperations.computeFarthestVertex(dbGraph);
		assertThat(youngestChild.getName()).isEqualTo("child10");
	}

	@Test
	public void findRootTable() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var dbGraph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var rootTable = GraphOperations.findRoot(dbGraph);
		assertThat(rootTable.getName()).isEqualTo("parent1");
	}

	@Test
	public void allPaths() {
		var dbSchema = dbSchemaService.getDbSchema(DATABASE_NAME);
		var dbGraph = GraphOperations.getDbSchemaAsGraph(dbSchema);
		var path = GraphOperations.removalSequence(dbGraph);
		var tablesSequence = path.stream().map(DbTable::getName).collect(Collectors.toList());
		assertThat(tablesSequence).containsExactly("child10", "child9", "child8", "child7", "child6", "child5",
				"child4", "child3", "child2", "child1", "parent1");
	}

}

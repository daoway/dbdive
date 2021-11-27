package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbSchema;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.graphml.GraphMLExporter;

import java.io.FileWriter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphOperations {

	public static DefaultDirectedGraph<DbTable, DefaultEdge> getDbSchemaAsGraph(DbSchema db) {
		var dbGraph = new DefaultDirectedGraph<DbTable, DefaultEdge>(DefaultEdge.class);
		db.getTables().forEach((name, dbTable) -> dbGraph.addVertex(dbTable));
		db.getTables().forEach((name, dbTable) -> {
			var relations = dbTable.getExportedForeignKeys();
			relations.forEach((relation) -> dbGraph.addEdge(dbTable, db.getTables().get(relation.getFkTableName())));
		});
		return dbGraph;
	}

	public static void exportAsGraphML(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph, String outFileName) {
		var exporter = new GraphMLExporter<DbTable, DefaultEdge>();
		exporter.setExportVertexLabels(true);
		exporter.setExportEdgeLabels(true);
		try (var writer = new FileWriter(outFileName)) {
			exporter.exportGraph(dbGraph, writer);
		}
		catch (Exception exception) {
			log.error("Error : ", exception);
		}
	}

}

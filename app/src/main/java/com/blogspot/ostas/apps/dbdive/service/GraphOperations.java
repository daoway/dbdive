package com.blogspot.ostas.apps.dbdive.service;

import com.blogspot.ostas.apps.dbdive.model.DbSchema;
import com.blogspot.ostas.apps.dbdive.model.DbTable;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxCellRenderer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.graphml.GraphMLExporter;
import org.jgrapht.traverse.BreadthFirstIterator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphOperations {

	private static final GraphMLExporter<DbTable, DefaultEdge> exporter;
	static {
		exporter = new GraphMLExporter<>();
		exporter.setExportVertexLabels(true);
		exporter.setExportEdgeLabels(true);
	}

	public static DefaultDirectedGraph<DbTable, DefaultEdge> getDbSchemaAsGraph(DbSchema db) {
		var dbGraph = new DefaultDirectedGraph<DbTable, DefaultEdge>(DefaultEdge.class);
		db.getTables().forEach((name, dbTable) -> dbGraph.addVertex(dbTable));
		db.getTables().forEach((name, dbTable) -> {
			var relations = dbTable.getExportedForeignKeys();
			if (relations != null) {
				relations
						.forEach((relation) -> dbGraph.addEdge(dbTable, db.getTables().get(relation.getFkTableName())));
			}
		});
		return dbGraph;
	}

	public static void exportAsGraphML(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph, String outFileName) {
		try (var writer = new FileWriter(outFileName)) {
			exporter.exportGraph(dbGraph, writer);
		}
		catch (IOException exception) {
			log.error("Error : ", exception);
		}
	}

	@SneakyThrows
	public static void exportAsPng(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph, String outFileName) {
		var exportedFile = new File(outFileName);
		var graphAdapter = new JGraphXAdapter<>(dbGraph);
		var layout = new mxHierarchicalLayout(graphAdapter);

		layout.execute(graphAdapter.getDefaultParent());

		var image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
		ImageIO.write(image, "PNG", exportedFile);
	}

	public static DbTable computeFarthestVertex(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph) {
		var bfs = new BreadthFirstIterator<>(dbGraph);
		DbTable farthest = null;
		var dist = Integer.MIN_VALUE;
		while (bfs.hasNext()) {
			var v = bfs.next();
			var depth = bfs.getDepth(v);
			if (dist < depth) {
				farthest = v;
				dist = depth;
			}
		}
		return farthest;
	}

	public static DbTable findRoot(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph) {
		var measurer = new GraphMeasurer<>(dbGraph);
		var center = measurer.getGraphCenter();
		if (center.size() != 1) {
			throw new RuntimeException("not implemented yet");
		}
		return center.iterator().next();
	}

	public static List<DbTable> removalSequence(DefaultDirectedGraph<DbTable, DefaultEdge> dbGraph) {
		var to = GraphOperations.computeFarthestVertex(dbGraph);
		var from = GraphOperations.findRoot(dbGraph);

		var allDirectedPaths = new AllDirectedPaths<>(dbGraph);
		var possiblePathList = allDirectedPaths.getAllPaths(from, to, false, Integer.MAX_VALUE);
		if(possiblePathList.size()!=1){
			throw new RuntimeException("not implemented yet");
		}
		var path = possiblePathList.get(0).getVertexList();
		Collections.reverse(path);
		return path;
	}

	public static Set<DbTable> cycles(DefaultDirectedGraph<DbTable,DefaultEdge> graph){
		var cycleDetector = new CycleDetector<>(graph);
		return cycleDetector.findCycles();
	}


}

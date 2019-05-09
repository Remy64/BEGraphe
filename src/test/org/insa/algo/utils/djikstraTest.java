package org.insa.algo.utils;

import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.Node;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;
import org.junit.BeforeClass;
import org.junit.Test;

public class djikstraTest {
	
	
	private static Graph graph1, graph2, graph3;
	private static Node noeud_origine, noeud_destination;
	private static Path path1, path2, path3;
	private static DijkstraAlgorithm dijkstra1, dijkstra2, dijkstra3;
	
	@BeforeClass
	public static void initAll() throws Exception {
	String mapName1, mapName2, mapName3; 
	mapName1="C:\\Users\\remyb\\Desktop\\BE_Graphes\\BEGraphe\\carre.mapgr";
	mapName2="C:\\Users\\remyb\\Desktop\\BE_Graphes\\BEGraphe\\haute-garonne.mapgr";
	mapName3="C:\\Users\\remyb\\Desktop\\BE_Graphes\\BEGraphe\\insa.mapgr";
    GraphReader reader = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapName1)))); 
    graph1 = reader.read();
    reader = new BinaryGraphReader(
                    new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));
    graph2 = reader.read();
    reader = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapName3))));
    graph3 = reader.read();
    noeud_origine = graph1.get(1);
    noeud_destination = graph2.get(2);
    dijkstra1 = new DijkstraAlgorithm(new ShortestPathData(graph1, noeud_origine, noeud_destination, ArcInspectorFactory.getAllFilters().get(0)));
    dijkstra2 = new DijkstraAlgorithm(new ShortestPathData(graph2, noeud_origine, noeud_destination, ArcInspectorFactory.getAllFilters().get(0)));
    dijkstra3 = new DijkstraAlgorithm(new ShortestPathData(graph2, noeud_origine, noeud_destination, ArcInspectorFactory.getAllFilters().get(0)));
    path1 = dijkstra1.run().getPath();
    path2 = dijkstra1.run().getPath();
    path3 = dijkstra1.run().getPath();
	}
	
	@Test
	public void testIsValid() {
        assertTrue(path1.isValid());
        assertTrue(path2.isValid());
        assertTrue(path3.isValid());
	}
	
	
	
	
	


}

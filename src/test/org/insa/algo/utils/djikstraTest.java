package org.insa.algo.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Arc;
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
	
	
	private static Graph graph1, graph2, graph3, graph4, graph5;
	private static Node origin1, dest1, origin2, dest2, origin3, dest3, origin4, dest4, origin5, dest5;
	private static Path path1d, path1b, path2d, path2b, path3d, path3b, path4d, path4b, path5d, path5b;
	private static DijkstraAlgorithm dijkstra1, dijkstra2, dijkstra3, dijkstra4, dijkstra5;
	private static BellmanFordAlgorithm bellman1, bellman2, bellman3, bellman4, bellman5;
	
	@BeforeClass
	public static void initAll() throws Exception {
	String mapDirectory, mapName1, mapPath1, mapName2, mapPath2, mapName3, mapPath3, mapName4, mapPath4, mapName5, mapPath5;
	mapDirectory="/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/";
	mapName1="carre.mapgr";
	mapName2="haute-garonne.mapgr";
	mapName3="insa.mapgr";
	mapName4="carre-dense.mapgr";
	mapName5="guadeloupe.mapgr";
	mapPath1=mapDirectory+mapName1;
	mapPath2=mapDirectory+mapName2;
	mapPath3=mapDirectory+mapName3;
	mapPath4=mapDirectory+mapName4;
	mapPath5=mapDirectory+mapName5;
	
    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath1))));
    graph1 = reader.read();
    reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath2))));
    graph2 = reader.read();
    reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath3))));
    graph3 = reader.read();
    reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath4))));
    graph4 = reader.read();
    reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath5))));
    graph5 = reader.read();
    
    origin1 = graph1.get(1);
    dest1 = graph1.get(5);
    origin2 = graph2.get(7);
    dest2 = graph2.get(18);
    origin3 = graph3.get(2);
    dest3 = graph3.get(15);
    origin4 = graph4.get(61);
    dest4 = graph4.get(27);
    origin5 = graph5.get(52);
    dest5 = graph5.get(38);
    
    dijkstra1 = new DijkstraAlgorithm(new ShortestPathData(graph1, origin1, dest1, ArcInspectorFactory.getAllFilters().get(0)));
    dijkstra2 = new DijkstraAlgorithm(new ShortestPathData(graph2, origin2, dest2, ArcInspectorFactory.getAllFilters().get(2)));
    dijkstra3 = new DijkstraAlgorithm(new ShortestPathData(graph3, origin3, dest3, ArcInspectorFactory.getAllFilters().get(2)));
    dijkstra4 = new DijkstraAlgorithm(new ShortestPathData(graph4, origin4, dest4, ArcInspectorFactory.getAllFilters().get(0)));
    dijkstra5 = new DijkstraAlgorithm(new ShortestPathData(graph5, origin5, dest5, ArcInspectorFactory.getAllFilters().get(2)));
    
    path1d = dijkstra1.run().getPath();
    path2d = dijkstra2.run().getPath();
    path3d = dijkstra3.run().getPath();
    path4d = dijkstra4.run().getPath();
    path5d = dijkstra5.run().getPath();
    
    bellman1 = new BellmanFordAlgorithm(new ShortestPathData(graph1, origin1, dest1, ArcInspectorFactory.getAllFilters().get(0)));
    bellman2 = new BellmanFordAlgorithm(new ShortestPathData(graph2, origin2, dest2, ArcInspectorFactory.getAllFilters().get(2)));
    bellman3 = new BellmanFordAlgorithm(new ShortestPathData(graph3, origin3, dest3, ArcInspectorFactory.getAllFilters().get(2)));
    bellman4 = new BellmanFordAlgorithm(new ShortestPathData(graph4, origin4, dest4, ArcInspectorFactory.getAllFilters().get(0)));
    bellman5 = new BellmanFordAlgorithm(new ShortestPathData(graph5, origin5, dest5, ArcInspectorFactory.getAllFilters().get(2)));
    
    path1b = bellman1.run().getPath();
    path2b = bellman2.run().getPath();
    path3b = bellman3.run().getPath();
    path4b = bellman4.run().getPath();
    path5b = bellman5.run().getPath();
	}
	
	@Test
	public void pathsAreValid() {
        assertTrue(path1d.isValid());
        assertTrue(path2d.isValid());
        assertTrue(path3d.isValid());
        assertTrue(path4d.isValid());
        assertTrue(path5d.isValid());
	}
	
	@Test
	public void pathMatch() {
		assertEquals(path1d.getArcs().size(), path1b.getArcs().size());
		assertEquals(path2d.getArcs().size(), path2b.getArcs().size());
		assertEquals(path3d.getArcs().size(), path3b.getArcs().size());
		assertEquals(path4d.getArcs().size(), path4b.getArcs().size());
		assertEquals(path5d.getArcs().size(), path5b.getArcs().size());
		
		assertEquals(path1d.getOrigin().getId(), path1b.getOrigin().getId());
		assertEquals(path2d.getOrigin().getId(), path2b.getOrigin().getId());
		assertEquals(path3d.getOrigin().getId(), path3b.getOrigin().getId());
		assertEquals(path4d.getOrigin().getId(), path4b.getOrigin().getId());
		assertEquals(path5d.getOrigin().getId(), path5b.getOrigin().getId());
		
	/*	for(int i=0; i<path1d.getArcs().size(); i++) {
			assertEquals(path1d.getArcs().get(i).getDestination().getId(), (path1b.getArcs().get(i).getDestination().getId()));
		}*/
		for(int i=0; i<path2d.getArcs().size(); i++) {
			assertEquals(path2d.getArcs().get(i).getDestination().getId(), (path2b.getArcs().get(i).getDestination().getId()));
		}
		for(int i=0; i<path3d.getArcs().size(); i++) {
			assertEquals(path3d.getArcs().get(i).getDestination().getId(), (path3b.getArcs().get(i).getDestination().getId()));
		}
		for(int i=0; i<path4d.getArcs().size(); i++) {
			assertEquals(path4d.getArcs().get(i).getDestination().getId(), (path4b.getArcs().get(i).getDestination().getId()));
		}
		for(int i=0; i<path5d.getArcs().size(); i++) {
			assertEquals(path5d.getArcs().get(i).getDestination().getId(), (path5b.getArcs().get(i).getDestination().getId()));
		}
	}
}

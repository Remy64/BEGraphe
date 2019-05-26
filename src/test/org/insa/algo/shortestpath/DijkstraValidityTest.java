package org.insa.algo.shortestpath;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.*;

public class DijkstraValidityTest {

	private static ArrayList<Path[]> paths;

	public static Path getShortestPath(String mapName, int originId, int destId, int mode, char algo) throws Exception {
		String mapDirectory = "C:\\Users\\perlo\\Desktop\\C\\BEGraphe\\maps\\";
		String mapFormat = ".mapgr";
		String mapPath = mapDirectory + mapName + mapFormat;
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath))));
		Graph graph = reader.read();
		Node origin = graph.get(originId);
	    Node dest = graph.get(destId);
	    ShortestPathAlgorithm shortestPathAlgo;
	    switch(algo) {
	    case 'b':
	    	shortestPathAlgo = new BellmanFordAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'd':
	    	shortestPathAlgo = new DijkstraAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'a':
	    	shortestPathAlgo = new AStarAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    default:
	    	throw new Exception("Code d'algorithme incorrect. Entrez d, b ou a");
	    }
	    return shortestPathAlgo.run().getPath();
	}
	
	public static Path[] getShortestPaths(String mapName, int originId, int destId, int mode) {
		try {
			Path[] paths = new Path[2];
			paths[0] = getShortestPath(mapName, originId, destId, mode, 'b');
			paths[1] = getShortestPath(mapName, originId, destId, mode, 'd');
			return paths;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@BeforeClass
	public static void initAll() throws Exception {
		paths = new ArrayList<Path[]>();
		paths.add(getShortestPaths("carre-dense", 1, 5, 0));
		paths.add(getShortestPaths("haute-garonne", 7, 18, 2));
		paths.add(getShortestPaths("insa", 2, 15, 2));
		paths.add(getShortestPaths("aveyron", 61, 27, 0));
		paths.add(getShortestPaths("gironde", 52, 38, 2));
	}
	
	@Test
	public void pathsAreValid() {
		for(Path[] un_path : paths) {
			assertTrue(un_path[1].isValid());	
		}
	}
	
	@Test
	public void pathsMatch() {
		for(Path[] un_path : paths) {
			assertEquals(un_path[0].getArcs().size(), un_path[1].getArcs().size());
			assertEquals(un_path[0].getOrigin().getId(), un_path[1].getOrigin().getId());
			for(int i=0; i<un_path[0].getArcs().size(); i++) {
				assertEquals(un_path[0].getArcs().get(i).getDestination().getId(), un_path[1].getArcs().get(i).getDestination().getId());
			}
		}
	}
}

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
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.*;

public class DijkstraValidityTest {

	private static ArrayList<ShortestPathSolution[]> solutions;

	public static ShortestPathSolution getShortestPath(String mapName, int originId, int destId, int mode, char algo) throws Exception {
		String mapPath="C:\\Users\\remyb\\Desktop\\BE_Graphes\\BEGraphe\\" + mapName + ".mapgr";
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
	    return shortestPathAlgo.run();
	}
	
	public static ShortestPathSolution[] getShortestPaths(String mapName, int originId, int destId, int mode) {
		try {
			ShortestPathSolution[] solutions = new ShortestPathSolution[2];
			solutions[0] = getShortestPath(mapName, originId, destId, mode, 'b');
			solutions[1] = getShortestPath(mapName, originId, destId, mode, 'd');
			return solutions;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@BeforeClass
	public static void initAll() throws Exception {
		solutions = new ArrayList<ShortestPathSolution[]>();
		
		//solutions.add(getShortestPaths("carre-dense", 1, 160393, 0)); // carte non routière, chemin court, mode distance
		//solutions.add(getShortestPaths("carre-dense", 1, 160393, 2)); // carte non routière, chemin court, mode temps
		//solutions.add(getShortestPaths("carre-dense", 14660, 99742, 0)); // carte non routière, chemin long, mode distance
		//solutions.add(getShortestPaths("carre-dense", 14660, 99742, 2)); // carte non routière, chemin long, mode temps
		//solutions.add(getShortestPaths("haute-garonne", 12, 12, 0)); // carte routière, chemin nul, mode distance
		//solutions.add(getShortestPaths("haute-garonne", 7, 18, 2)); // carte routière, chemin court, mode temps
		//solutions.add(getShortestPaths("haute-garonne", 7, 18, 0)); // carte routière, chemin court, mode distance
		//solutions.add(getShortestPaths("haute-garonne", 128073, 107325, 2)); // carte routière, chemin long, mode temps
		//solutions.add(getShortestPaths("haute-garonne", 128073, 107325, 0)); // carte routière, chemin long, mode distance
		//solutions.add(getShortestPaths("insa", 539, 247, 2)); // chemin inexistant, mode temps

	}
	
	@Test
	public void pathsAreValid() {
		for(ShortestPathSolution[] une_solution : solutions) {
			assertTrue(une_solution[1].getPath().isValid());	
		}
	}
	
	@Test
	public void pathsMatch() {
		for(ShortestPathSolution[] une_solution : solutions) {
			if(!(une_solution[0].getStatus() == Status.INFEASIBLE)) {
			assertEquals(une_solution[0].getPath().size(), une_solution[1].getPath().size());
			assertEquals(une_solution[0].getPath().getOrigin().getId(), une_solution[1].getPath().getOrigin().getId());
			for(int i=0; i<une_solution[0].getPath().getArcs().size(); i++) {
				assertEquals(une_solution[0].getPath().getArcs().get(i).getDestination().getId(), une_solution[1].getPath().getArcs().get(i).getDestination().getId());
				}
			}
			else {
				assertTrue(une_solution[1].getStatus() == une_solution[0].getStatus());
			}
		}
	}
}

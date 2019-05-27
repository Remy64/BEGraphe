package org.insa.algo.shortestpath;

public class AStarValidityTest extends DijkstraValidityTest {
	
	public static ShortestPathSolution[] getShortestPaths(String mapName, int originId, int destId, int mode) {
		try {
			ShortestPathSolution[] solutions = new ShortestPathSolution[2];
			solutions[0] = getShortestPath(mapName, originId, destId, mode, 'b');
			solutions[1] = getShortestPath(mapName, originId, destId, mode, 'a');
			return solutions;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}

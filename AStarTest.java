package org.insa.algo.shortestpath;

import org.insa.graph.*;

public class AStarTest extends DijkstraTest {
	
	public static Path[] getShortestPaths(String mapName, int originId, int destId, int mode) {
		try {
			Path[] paths = new Path[2];
			paths[0] = getShortestPath(mapName, originId, destId, mode, 'b');
			paths[1] = getShortestPath(mapName, originId, destId, mode, 'a');
			return paths;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}

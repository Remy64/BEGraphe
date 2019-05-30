package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Point;

public class Azerty {

	public static void runShortestPathAlgorithms(String mapName, Mode mode, int nbIterations, int distanceMax, int distanceMin) throws Exception {
		//graph extraction
		String mapDirectory = "C:\\Users\\perlo\\Desktop\\C\\BEGraphe\\maps\\";
		String mapFormat = ".mapgr";
		String mapPath = mapDirectory + mapName + mapFormat;
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath))));
		Graph graph = reader.read();
		
		System.out.println("carte : " + mapName);
		System.out.println("mode : " + mode.name());
		//iterations
		int i = 0;
		int nbIterDijkstraPlusRapide = 0;
		long dijkstraCumulatedSolvingTime = 0;
		long aStarCumulatedSolvingTime = 0;
		int dijkstraCumulatedNumberOfSearchedNodes = 0;
		int aStarCumulatedNumberOfSearchedNodes = 0;
		while(i<nbIterations) {
			//random node_id generation
			int originId = (int) (graph.size() * Math.random());
			int destId = (int) (graph.size() * Math.random());
			
			Node origin = graph.get(originId);
		    Node dest = graph.get(destId);
		    
		    if(Point.distance(origin.getPoint(), dest.getPoint())<=distanceMax && Point.distance(origin.getPoint(), dest.getPoint())>=distanceMin) {
			    DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode==Mode.LENGTH ? 0 : 2)));
			    AStarAlgorithm aStarAlgo = new AStarAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode==Mode.LENGTH ? 0 : 2)));
			    DijkstraSolution dijkstraSolution = (DijkstraSolution)(dijkstraAlgo.run());
			    DijkstraSolution aStarSolution = (DijkstraSolution)(aStarAlgo.run());
			    if(aStarSolution.isFeasible()) {
					i++;
					if(dijkstraSolution.getSolvingTime().toMillis()<aStarSolution.getSolvingTime().toMillis()) {
						nbIterDijkstraPlusRapide++;
					}
					dijkstraCumulatedSolvingTime += dijkstraSolution.getSolvingTime().toMillis();
					aStarCumulatedSolvingTime += aStarSolution.getSolvingTime().toMillis();
					dijkstraCumulatedNumberOfSearchedNodes += dijkstraSolution.getNbExploredNodes();
					aStarCumulatedNumberOfSearchedNodes += aStarSolution.getNbExploredNodes();
			    }
		    }
		    
		}
		System.out.println("nb iter dijkstra plus rapide : " + nbIterDijkstraPlusRapide);
		System.out.println("dijkstra cumulated solving time : " + dijkstraCumulatedSolvingTime);
		System.out.println("a-star cumulated solving time : " + aStarCumulatedSolvingTime);
		System.out.println("dijkstra cumulated number of explored nodes : " + dijkstraCumulatedNumberOfSearchedNodes);
		System.out.println("a-star cumulated number of explored nodes : " + aStarCumulatedNumberOfSearchedNodes);
	}
	
	public static void main(String[] args) throws Exception {
		int nbIterations = 100;
		int distanceMin = 0;
		int distanceMax = Integer.MAX_VALUE;
		
		System.out.println("nombre d'itérations : " + nbIterations);
		System.out.println("");
		
		//runShortestPathAlgorithms("carre-dense", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("carre", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("fractal-spiral", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("fractal", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("haute-garonne", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("insa", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("aveyron", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("gironde", Mode.LENGTH, nbIterations, distanceMax, distanceMin);
		
		//runShortestPathAlgorithms("carre-dense", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("carre", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("fractal-spiral", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("fractal", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("haute-garonne", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("insa", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("aveyron", Mode.TIME, nbIterations, distanceMax, distanceMin);
		//runShortestPathAlgorithms("gironde", Mode.TIME, nbIterations, distanceMax, distanceMin);
    }
}

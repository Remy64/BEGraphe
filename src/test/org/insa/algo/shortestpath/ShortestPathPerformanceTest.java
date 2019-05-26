package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.insa.graph.io.BinaryGraphReader;
//import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
//import org.insa.graph.io.PathReader;
//import org.insa.graphics.drawing.Drawing;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.*;

public class ShortestPathPerformanceTest {

	public static void runShortestPathAlgorithms(String mapName, Mode mode, int nbIterations) throws Exception {
		//graph extraction
		String mapDirectory = "C:\\Users\\perlo\\Desktop\\C\\BEGraphe\\maps\\";
		String mapFormat = ".mapgr";
		String mapPath = mapDirectory + mapName + mapFormat;
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath))));
		Graph graph = reader.read();
		
		//file object creation
		String fileNameFirstPart = mapName + "_" + (mode==Mode.LENGTH ? "distance" : "temps") + "_" + nbIterations + "_";
		java.nio.file.Path dijkstraFile = Paths.get(fileNameFirstPart + "dijkstra.txt");
		java.nio.file.Path aStarFile = Paths.get(fileNameFirstPart + "a-star.txt");
		
		List<String> dijkstraLines = Arrays.asList(
			"nom de la carte : " + mapName,
			"mode : " + (mode==Mode.LENGTH ? "distance" : "temps"),
			"nombre d'iterations : " + nbIterations,
			"algorithme : dijkstra",
			"",
			""
		);
		List<String> aStarLines = Arrays.asList(
			"nom de la carte : " + mapName,
			"mode : " + (mode==Mode.LENGTH ? "distance" : "temps"),
			"nombre d'iterations : " + nbIterations,
			"algorithme : a-star",
			"",
			""
		);
		try {
			Files.write(dijkstraFile, dijkstraLines, Charset.forName("UTF-8"));
			Files.write(aStarFile, aStarLines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.out.println("impossible d'ecrire dans le fichier");
		}
		
		//iterations
		int i = 0;
		while(i<nbIterations) {
			//random node_id generation
			int originId = (int) (graph.size() * Math.random());
			int destId = (int) (graph.size() * Math.random());
			Node origin = graph.get(originId);
		    Node dest = graph.get(destId);
		    
		    DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode==Mode.LENGTH ? 0 : 2)));
		    AStarAlgorithm aStarAlgo = new AStarAlgorithm(new ShortestPathData(graph, origin, dest, ArcInspectorFactory.getAllFilters().get(mode==Mode.LENGTH ? 0 : 2)));
		    ShortestPathSolution dijkstraSolution = dijkstraAlgo.run();
		    ShortestPathSolution aStarSolution = aStarAlgo.run();
		    if(dijkstraSolution.isFeasible()) {
				i++;
			    dijkstraLines = Arrays.asList(
			    	"iteration : " + i,
					"id du noeud origine : " + originId,
					"id du noeud destination : " + destId,
					"le chemin trouve contient : " + dijkstraSolution.getPath().size() + " arcs",
					"temps d'execution de l'algo : " + dijkstraSolution.getSolvingTime().toMillis() + " ms",
					""
				);
			    aStarLines = Arrays.asList(
			    	"iteration : " + i,
					"id du noeud origine : " + originId,
					"id du noeud destination : " + destId,
					"le chemin trouve contient : " + aStarSolution.getPath().size() + " arcs",
					"temps d'execution de l'algo : " + aStarSolution.getSolvingTime().toMillis() + " ms",
					""
				);
				try {
					Files.write(dijkstraFile, dijkstraLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
					Files.write(aStarFile, aStarLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
				} catch (IOException e) {
					System.out.println("impossible d'ecrire dans le fichier");
				}
		    }
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nbIterations = 10;
		runShortestPathAlgorithms("carre-dense", Mode.LENGTH, nbIterations);
		runShortestPathAlgorithms("haute-garonne", Mode.TIME, nbIterations);
		runShortestPathAlgorithms("insa", Mode.TIME, nbIterations);
		runShortestPathAlgorithms("aveyron", Mode.LENGTH, nbIterations);
		runShortestPathAlgorithms("gironde", Mode.TIME, nbIterations);
    }
}

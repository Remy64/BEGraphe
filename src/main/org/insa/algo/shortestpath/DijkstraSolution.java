package org.insa.algo.shortestpath;

import org.insa.graph.Path;

public class DijkstraSolution extends ShortestPathSolution {
	private int nbExploredNodes;
    private int nbMarkedNodes;
    private int tailleMaxTas;
    
    public DijkstraSolution(ShortestPathData data, Status status, Path path, int nbExploredNodes, int nbMarkedNodes, int tailleMaxTas) {
        super(data, status, path);
        this.nbExploredNodes = nbExploredNodes;
        this.nbMarkedNodes = nbMarkedNodes;
        this.tailleMaxTas = tailleMaxTas;
    }
    
    public int getNbExploredNodes() {
    	return this.nbExploredNodes;
    }
    
    public int getNbMarkedNodes() {
    	return this.nbMarkedNodes;
    }
    
    public int getTailleMaxTas() {
    	return this.tailleMaxTas;
    }
}

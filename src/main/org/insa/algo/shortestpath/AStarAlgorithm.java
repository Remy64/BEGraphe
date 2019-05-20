package org.insa.algo.shortestpath;

import org.insa.graph.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected LabelStar[] initLabel(ShortestPathData data) {
    	Graph graph = data.getGraph();
    	int nbNodes = graph.size();
    	LabelStar[] label = new LabelStar[nbNodes];
    	for(int i = 0; i < nbNodes; i++) {
    		label[i] = new LabelStar(i, graph, data.getDestination(), data.getMode(), data.getMaximumSpeed());
    	}
    	return label;
    }
}

package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public Label [] initDjikstra(Graph graph, int dest, Mode mode, double speed) {
    	LabelStar[] label = new LabelStar[graph.size()];
    	for(int i =0; i < graph.size(); i++) {
    		label[i] = new LabelStar(i, graph, dest, mode, speed);
    	}
    	return label;
    	
    }

}

package org.insa.algo.shortestpath;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected LabelStar[] initLabel(ShortestPathData data) {
    	LabelStar[] label = new LabelStar[data.getGraph().size()];
    	for(int i = 0; i < label.length; i++) {
    		label[i] = new LabelStar(i, data);
    	}
    	return label;
    }
}

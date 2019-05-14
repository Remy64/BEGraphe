package org.insa.algo.shortestpath;


import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Graph;

public class LabelStar extends Label {
	
	private double cout_estime;
	
	public LabelStar(int node, Graph graph, int dest, Mode mode, double speed) {
		super(node);
		switch(mode) {
		case LENGTH:
			this.cout_estime = graph.get(node).getPoint().distanceTo(graph.get(dest).getPoint());
			break;
		case TIME:
			this.cout_estime = graph.get(node).getPoint().distanceTo(graph.get(dest).getPoint()) / speed;
			break;
			
		
		
		}
		
	}

	
	public double getTotalCost() {
		return this.cout_estime + this.getCost();
	}

}

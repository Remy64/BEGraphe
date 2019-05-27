package org.insa.algo.shortestpath;
import org.insa.graph.*;
import org.insa.algo.AbstractInputData.Mode;
//import org.insa.algo.ArcInspector;
public class LabelStar extends Label {
	private double destCost;
	public LabelStar(int node, Graph graph, Node dest, Mode mode, int maxSpeed) {
		super(node);
		double distance = Point.distance(dest.getPoint(), graph.get(this.getNode()).getPoint());
		switch(mode) {
		case LENGTH:
			this.destCost = distance;
			break;
		case TIME:
			this.destCost = 3.6*distance/(Math.max(maxSpeed, graph.getGraphInformation().getMaximumSpeed()));
			break;
		}
	}
	public double getTotalCost() {
		return this.getCost()+this.destCost;
	}
}

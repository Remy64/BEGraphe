package org.insa.algo.shortestpath;
import org.insa.graph.*;
import org.insa.algo.AbstractInputData.Mode;
//import org.insa.algo.ArcInspector;
public class LabelStar extends Label {
	private double cout_dest;
	public LabelStar(int node, Graph graph, Node dest, Mode mode, int maxSpeed) {
		super(node);
		double distance = Point.distance(dest.getPoint(), graph.get(this.getNode()).getPoint());
		switch(mode) {
		case LENGTH:
			this.cout_dest = distance;
			break;
		case TIME:
			this.cout_dest = 3.6*distance/(Math.max(maxSpeed, graph.getGraphInformation().getMaximumSpeed()));
			break;
		}
	}
	public double getTotalCost() {
		return this.getCost()+this.cout_dest;
	}
}

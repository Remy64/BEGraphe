package org.insa.algo.shortestpath;
import org.insa.graph.*;
import org.insa.algo.AbstractInputData.Mode;
//import org.insa.algo.ArcInspector;
public class LabelStar extends Label {
	private double destCost;
	public LabelStar(int node, Graph graph, int dest, Mode mode, int maxSpeed) {
		super(node);
		double distance = Point.distance(graph.get(dest).getPoint(), graph.get(node).getPoint());
		switch(mode) {
		case LENGTH:
			this.destCost = distance;
			break;
		case TIME:
			//on suppose que maxSpeed est un argument (inclus dans ShortestPathData) qui ecrase la vitesse maximale du graphe lorsqu'il ne vaut pas GraphStatistics.NO_MAXIMUM_SPEED
			this.destCost = 3.6*distance/(maxSpeed==GraphStatistics.NO_MAXIMUM_SPEED ? graph.getGraphInformation().getMaximumSpeed() : (double)maxSpeed);
			break;
		}
	}
	public double getTotalCost() {
		return this.getCost()+this.destCost;
	}
}

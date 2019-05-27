package org.insa.algo.shortestpath;

import org.insa.graph.GraphStatistics;

public class LabelStar extends Label {
	private double destCost;
	public LabelStar(int nodeId, ShortestPathData data) {
		super(nodeId);
		double distance = data.getDestination().getPoint().distanceTo(data.getGraph().get(nodeId).getPoint());
		switch(data.getMode()) {
		case LENGTH:
			this.destCost = distance;
			break;
		case TIME:
			//on suppose que maxSpeed est un argument qui ecrase la vitesse maximale du graphe lorsqu'il ne vaut pas GraphStatistics.NO_MAXIMUM_SPEED
			//on multiplie distance/vitesse par 3.6 afin de convertir la vitesse de km/h a m/s
			this.destCost = 3.6*distance/(data.getMaximumSpeed()==GraphStatistics.NO_MAXIMUM_SPEED ? data.getGraph().getGraphInformation().getMaximumSpeed() : (double)(data.getMaximumSpeed()));
			break;
		}
	}
	public double getTotalCost() {
		return this.getCost()+this.destCost;
	}
}

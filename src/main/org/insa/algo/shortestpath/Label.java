package org.insa.algo.shortestpath;
import org.insa.graph.*;
public class Label implements Comparable<Label> {
	private int nodeId;
	private boolean marked;
	private double cost;
	private Arc parent;
	
	public Label(int nodeId) {
		this.nodeId = nodeId;
		this.cost = Double.POSITIVE_INFINITY;
		this.marked = false;
		this.parent = null;
	}
	
	public int getNode() {
		return this.nodeId;
	}
	
	public Arc getFather() {
		return this.parent;
	}
	
	public void setFather(Arc parent) {
		this.parent = parent;
	}
	
	public boolean isMarked() {
		return this.marked;
	}
	
	public void mark() {
		this.marked = true;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getTotalCost() {
		return this.getCost();
	}
	
	public int compareTo(Label a) {
		if(this.getTotalCost() < a.getTotalCost())
			return -1;
		else if(this.getTotalCost() == a.getTotalCost())
			return 0;
		else 
			return 1;
	}
}
package org.insa.algo.shortestpath;
import org.insa.graph.*;
public class Label implements Comparable<Label> {
	private int cur_sommet;
	private boolean marque;
	private double cout;
	private Arc pere;
	
	public int getNode() {
		return this.cur_sommet;
	}
	public Label(int node) {
		this.cur_sommet = node;
		this.cout = Double.POSITIVE_INFINITY;
		this.marque = false;
		this.pere = null; 
	}
	public Arc getFather() {
		return this.pere;
	}
	public void setFather(Arc papa) {
		this.pere = papa;
	}
	public boolean isMarked() {
		return this.marque;
	}
	public void mark() {
		this.marque = true;
	}
	public double getCost() {
		return this.cout;
	}	
	public void setCost(double cout) {
		this.cout = cout;
	}
	public int compareTo(Label a) {
		if(this.getCost() < a.getCost())
			return -1;
		else if(this.getCost() == a.getCost())
			return 0;
		else 
			return 1;
	}
}
package org.insa.algo.shortestpath;
import org.insa.graph.*;
public class Label {
	private Node cur_sommet;
	private boolean marque;
	private int cout;
	private Arc pere;
	
	public Node getNode() {
		return this.cur_sommet;
	}
	public Arc getFather() {
		return this.pere;
	}
	public boolean isMarked() {
		return this.marque;
	}
	public int getCost() {
		return this.cout;
	}	
	
}
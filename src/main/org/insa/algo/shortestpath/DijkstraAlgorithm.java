package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        Graph graph = data.getGraph();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        int nbNodes = graph.size();
        
        Label[] label = new Label[nbNodes];
        for(int i = 0; i < nbNodes; i++) {
        	label[i] = new Label(i);
        }
        int currentNode;
        label[data.getOrigin().getId()].setCost(0);
        tas.insert(label[data.getOrigin().getId()]);
        boolean allMarked = false;
        while(!allMarked) {
        	currentNode = tas.deleteMin().getNode();
        	label[currentNode].mark();
        	System.out.println("Le noeud :"+currentNode+"vient d'être marqué");
        	for(Arc successeur : graph.get(currentNode).getSuccessors()) {
        		int curSucc = successeur.getDestination().getId();
        		if(!label[curSucc].isMarked()) {
        			if(label[currentNode].getCost()+successeur.getMinimumTravelTime() < label[curSucc].getCost()) {
        				this.notifyNodeReached(successeur.getDestination());
        				label[curSucc].setCost(label[currentNode].getCost() + successeur.getMinimumTravelTime());
        				System.out.println("Insertion dans le tas : Noeud numero"+successeur.getDestination().getId());
        				tas.insert(label[successeur.getDestination().getId()]);
        				label[curSucc].setFather(successeur);
        			}
        		}
        	}
        	allMarked = true;
        	for(int j = 0; j < nbNodes && allMarked; j++) {
        		allMarked &= label[j].isMarked();
        	}
        }
     
        Label destination = label[data.getDestination().getId()];
        int currNode = destination.getNode();
        Stack<Arc> reversePath = new Stack<Arc>();
        while(currNode != data.getOrigin().getId()) {
        	reversePath.push(label[currNode].getFather());
        	currNode = label[currNode].getFather().getOrigin().getId();
        }
        ArrayList<Arc> liste_arcs = new ArrayList<Arc>();
        while(!reversePath.isEmpty()) {
        	liste_arcs.add(reversePath.pop());
        }
        Path path = new Path(graph, liste_arcs);
   
       solution = new ShortestPathSolution(data, Status.FEASIBLE, path);
        
        return solution;
    }

}
    


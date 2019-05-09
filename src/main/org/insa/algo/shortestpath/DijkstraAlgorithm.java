package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.EmptyPriorityQueueException;
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
        while(!label[data.getDestination().getId()].isMarked()) {
        	try {
	        	currentNode = tas.deleteMin().getNode();
	        	
	        	
	        	label[currentNode].mark();
	        	System.out.println("Le noeud :"+currentNode+"vient d'etre marque. Son cout est "+label[currentNode].getCost());
	        	for(Arc successeur : graph.get(currentNode).getSuccessors()) {
	        		int curSucc = successeur.getDestination().getId();
	        		if(!label[curSucc].isMarked() && data.isAllowed(successeur)) {
	        			if(label[currentNode].getCost()+data.getCost(successeur) < label[curSucc].getCost()) {
	        				this.notifyNodeReached(successeur.getDestination());
	        				label[curSucc].setCost(label[currentNode].getCost() + data.getCost(successeur));
	        				//System.out.println("Insertion dans le tas : Noeud numero"+successeur.getDestination().getId());
	        				tas.insert(label[successeur.getDestination().getId()]);
	        				label[curSucc].setFather(successeur);
	        			}
	        			System.out.println("Le tas a une taille de "+tas.size());
	        		}
	        	}
	        	allMarked = true;
	        	for(int j = 0; j < nbNodes && allMarked; j++) {
	        		allMarked &= label[j].isMarked();
	        	}
	        }
	     
	        catch(EmptyPriorityQueueException e) {
	    		ArrayList<Node> noeuds = new ArrayList<Node>();
	    		Path path = Path.createFastestPathFromNodes(graph, noeuds);
	    		solution = new ShortestPathSolution(data, Status.INFEASIBLE, path);
	    		return solution;
	    	}
        }
     
        Label destination = label[data.getDestination().getId()];
        int currNode = destination.getNode();
        Stack<Node> reversePath = new Stack<Node>();
        while(currNode != data.getOrigin().getId()) {
        	reversePath.push(label[currNode].getFather().getOrigin());
        	currNode = label[currNode].getFather().getOrigin().getId();
        }
        ArrayList<Node> liste_noeuds = new ArrayList<Node>();
        while(!reversePath.isEmpty()) {
        	liste_noeuds.add(reversePath.pop());
        }
        Path path = Path.createShortestPathFromNodes(graph, liste_noeuds);
   
       solution = new ShortestPathSolution(data, Status.FEASIBLE, path);
       System.out.println("Ce chemin contient : "+liste_noeuds.size()+" arcs");
       
        
        return solution;
    }

}

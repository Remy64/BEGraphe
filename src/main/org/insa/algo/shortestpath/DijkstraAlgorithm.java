package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.ElementNotFoundException;
import org.insa.algo.utils.EmptyPriorityQueueException;
import org.insa.algo.AbstractInputData.Mode;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label[] initLabel(ShortestPathData data) {
    	int nbNodes = data.getGraph().size();
    	Label[] label = new Label[nbNodes];
    	for(int i = 0; i < nbNodes; i++) {
    		label[i] = new Label(i);
    	}
    	return label;
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        
        // si le noeud d'origine est le noeud de destination, on renvoie un chemin contenant seulement le noeud destination/origine avec status faisable
        if(data.getOrigin().getId()==data.getDestination().getId()) {
    		solution = new ShortestPathSolution(data, Status.FEASIBLE, new Path(graph, data.getOrigin()));
    		return solution;
        }
        
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        Label[] label = this.initLabel(data);
        
		int nbExploredNodes = 1; //on inclut le noeud de depart
        int nbMarkedNodes = 0;
        int tailleMaxTas = tas.size();
        
        //on met le cout du sommet de depart à 0 et on insere son label dans le tas
        label[data.getOrigin().getId()].setCost(0);
        tas.insert(label[data.getOrigin().getId()]);
        
        int currentNode;
        while(!label[data.getDestination().getId()].isMarked()) {
        	try {
        		//on recupere le sommet de cout minimal dans le tas et on le marque
	        	currentNode = tas.deleteMin().getNode();
	        	label[currentNode].mark();
	        	nbMarkedNodes++;
	        	System.out.println("Le noeud : " + currentNode + " vient d'etre marque. Son cout est de " + label[currentNode].getCost());
	        	//on parcourt ses successeurs
	        	for(Arc successeur : graph.get(currentNode).getSuccessors()) {
	        		int curSucc = successeur.getDestination().getId();
	        		//si un successeur n'est pas marque et qu'il est autorise par le filtre entre en parametre
	        		if(!label[curSucc].isMarked() && data.isAllowed(successeur)) {
	        			//si la somme des couts du sommet courant et du successeur est inférieure au cout du successeur
	        			if(label[currentNode].getCost()+data.getCost(successeur) < label[curSucc].getCost()) {
	        				//permet d'afficher le noeud comme marqué
	        				this.notifyNodeReached(successeur.getDestination());
	        				//si on peut retirer du tas le label de ce successeur
	        				//alors on le fait pour ensuite inserer un label avec un cout a jour
	        				//sinon on incremente le compteur de noeuds explores et on vérifie si la taille du tas est maximale ou non
	        				try {
		        				tas.remove(label[curSucc]);
	        				} catch(ElementNotFoundException e) {
								nbExploredNodes++;
								if(tas.size()>tailleMaxTas) {
									tailleMaxTas = tas.size();
								}
	        				}
	        				//on applique les modifications au label
	        				label[curSucc].setCost(label[currentNode].getCost() + data.getCost(successeur));
	        				label[curSucc].setFather(successeur);
	        				//on insere le label dans le tas
	        				tas.insert(label[curSucc]);
	        				System.out.println(tas.isValid() ? ("Le tas est valide et a une taille de " + tas.size()) : "Le tas n'est pas valide");
	        			}
	        		}
	        	}
	        }
	        catch(EmptyPriorityQueueException e) {
	        	//si le tas est vide, alors cela signifie que les noeuds de depart et d'arrivee ne sont pas connexes
	        	//alors on renvoie un path vide avec status infaisable
	    		solution = new ShortestPathSolution(data, Status.INFEASIBLE, new Path(graph));
	    		return solution;
	    	}
        }
        
        Stack<Node> reversePath = new Stack<Node>();
        int currNode = data.getDestination().getId();
        reversePath.push(label[currNode].getFather().getDestination());
        while(currNode != data.getOrigin().getId()) {
        	reversePath.push(label[currNode].getFather().getOrigin());
        	currNode = label[currNode].getFather().getOrigin().getId();
        }
        ArrayList<Node> liste_noeuds = new ArrayList<Node>();
        while(!reversePath.isEmpty()) {
        	liste_noeuds.add(reversePath.pop());
        }
        
        Path path;
        if(data.getMode()==Mode.LENGTH) {
        	path = Path.createShortestPathFromNodes(graph, liste_noeuds);
        } else {
        	path = Path.createFastestPathFromNodes(graph, liste_noeuds);
        }
        solution = new ShortestPathSolution(data, Status.FEASIBLE, path);
        
		//TEST
		int nbTests = 10;
		String fileName = 
			data.getGraph().getMapName().toLowerCase() + "_" +
			(data.getMode()==Mode.LENGTH ? "distance" : "temps") + "_" +
			nbTests + "_" +
			(this.getClass().getName()=="org.insa.algo.shortestpath.DijkstraAlgorithm" ? "dijkstra" : "a-star") +
			".txt";
		java.nio.file.Path file = Paths.get(fileName);
		List<String> lines = Arrays.asList(
			"nombre de sommets explores : " + nbExploredNodes,
			"nombre de sommets marques : " + nbMarkedNodes,
			"taille maximale du tas : " + tailleMaxTas
		);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("impossible d'ecrire dans le fichier");
		}
		//END TEST
		
        return solution;
    }

}
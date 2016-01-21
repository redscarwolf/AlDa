package aufg3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sim.SYSimulation;
import aufg2.AdjacencyListUndirectedGraph;
import aufg2.Edge;
import aufg2.Graph;

public class myApp {

	public static void main(String[] args) {
		
		//Heuristic Test
		ScotlandYardHeuristic SYHeuristic;
		try {
			SYHeuristic = new ScotlandYardHeuristic();
		} catch(IOException e) {
			System.out.println("File net da");
			return;
		}
		SYHeuristic.estimatedCost(9, 9);
		SYHeuristic.estimatedCost(11, 1);
		//Heuristic Ende


		PriorityList<Integer> prioList = new PriorityList<>();
		
		prioList.insert(1, 0.0);
		prioList.insert(8, 2.0);
		prioList.insert(9, 5.0);
		prioList.insert(19, 7.2);
		prioList.print();
		
		Integer i = prioList.deleteMin(SYHeuristic, 19);
		System.out.println(i);
		prioList.print();
		
		if (!prioList.change(9, 7.0)) {
			System.out.println("ERROR change returned false: Vertex is not in PrioList.");
		}
		prioList.print();
		
		System.out.println(prioList.isEmty());
		
		
		/*
		// init
		Integer v0 = 0; // start Vertex
		Graph<Integer> g = createGraph();
		
		ShortestPath<Integer> sySp = new ShortestPath<Integer>(g, null);
	
		// do
		sySp.searchShortestPath(2, 4);
		List<Integer> l = sySp.getShortestPath();
		double dis = sySp.getDistance();
		System.out.println(l);
		System.out.println(dis);
		*/
		
		/*
		try {
			ScotlandYard.getGraph();
		} catch (FileNotFoundException ex) {
			System.out.printf("FileNotFound %n");
		}
		*/
		
		/*Graph<Integer> g = createGraph();
		ShortestPath<Integer> sySp = new ShortestPath<Integer>(g, null);
		
		sySp.searchShortestPath(65,157);
		System.out.println("Distance = " + sySp.getDistance()); // 9.0

		sySp.searchShortestPath(1,175);
		System.out.println("Distance = " + sySp.getDistance()); // 25.0

		sySp.searchShortestPath(1,173);
		System.out.println("Distance = " + sySp.getDistance()); // 22.0
		*/
	}
	
	private static AdjacencyListUndirectedGraph<Integer> createGraph() {
		AdjacencyListUndirectedGraph<Integer> undg = new AdjacencyListUndirectedGraph<>();
		
		Integer v0 = 0;
		Integer v1 = 1;
		Integer v2 = 2;
		Integer v3 = 3;
		Integer v4 = 4;
		Integer v5 = 5;
		Integer v6 = 6;
		Integer v7 = 7;
		Integer v8 = 8;
		
		undg.addVertex(v0);
		undg.addVertex(v1);
		undg.addVertex(v2);
		undg.addVertex(v3);
		undg.addVertex(v4);
		undg.addVertex(v5);
		undg.addVertex(v6);
		undg.addVertex(v7);
		undg.addVertex(v8);
		
		// Seite 3-56
		undg.addEdge(v0, v1, 1);
		undg.addEdge(v0, v2, 6);
		undg.addEdge(v0, v3, 2);
		undg.addEdge(v0, v4, 2);
		undg.addEdge(v1, v2, 4);
		undg.addEdge(v1, v5, 5);
		undg.addEdge(v2, v3, 3);
		undg.addEdge(v2, v7, 8);
		undg.addEdge(v4, v5, 4);
		undg.addEdge(v4, v6, 1);
		undg.addEdge(v5, v8, 7);
		undg.addEdge(v6, v7, 1);
		undg.addEdge(v7, v8, 1);
		
		// alle Kanten Anzeigen
   		List<Edge<Integer>> edgeList = undg.getEdgeList();
   		System.out.println(edgeList);
   		return undg;
	}

}

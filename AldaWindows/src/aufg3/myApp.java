package aufg3;

import java.util.List;

import aufg2.AdjacencyListUndirectedGraph;
import aufg2.Edge;
import aufg2.Graph;

public class myApp {

	public static void main(String[] args) {
		Graph<Integer> g = createGraph();
		ShortestPath<Integer> sySp = new ShortestPath<Integer>(g, null);
		
		sySp.searchShortestPath(65,157);
		System.out.println("Distance = " + sySp.getDistance()); // 9.0

		sySp.searchShortestPath(1,175);
		System.out.println("Distance = " + sySp.getDistance()); // 25.0

		sySp.searchShortestPath(1,173);
		System.out.println("Distance = " + sySp.getDistance()); // 22.0
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
		undg.addEdge(v1, v5, 2);
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

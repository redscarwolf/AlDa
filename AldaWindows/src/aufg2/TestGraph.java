package aufg2;

import java.util.List;

public class TestGraph {

	public static void main(String[] args) {
		testUndirected();
		System.out.println();
		testDirected();
		System.out.println();
		testTopologSort();
	}
		
	public static void testUndirected() {
		AdjacencyListUndirectedGraph<Integer> undg = new AdjacencyListUndirectedGraph<>();
		
		Integer v0 = 0;
		Integer v1 = 1;
		Integer v2 = 2;
		Integer v3 = 3;
		Integer v4 = 4;
		Integer v5 = 5;
		
		undg.addVertex(v0);
		undg.addVertex(v1);
		undg.addVertex(v2);
		undg.addVertex(v3);
		undg.addVertex(v4);
		undg.addVertex(v5);
		
		// Seite 3-37
		undg.addEdge(v1, v0, 1);
		// TODO
		undg.addEdge(v0, v1, 1);
		undg.addEdge(v0, v4, 1);
		undg.addEdge(v1, v2, 1);
		undg.addEdge(v1, v4, 1);
		undg.addEdge(v2, v4, 1);
		undg.addEdge(v2, v3, 1);
		undg.addEdge(v3, v4, 1);
		undg.addEdge(v5, v4, 1);
		
		printAndSearch(undg, v1);
		
	}
	
	private static <V> void printAndSearch(Graph<V> g, V v) {
		
		// alle Kanten Anzeigen
   		List<Edge<V>> edgeList = g.getEdgeList();
   		System.out.println(edgeList);
   		
   		// tiefensuche mit v als startknoten
   		List<V> listTief = GraphTraversion.depthFirstSearch(g, v); 
   		System.out.println(listTief);
   		// breitensuche
   		List<V> listBreit = GraphTraversion.breadthFirstSearch(g, v); 
   		System.out.println(listBreit);
	}
	
	public static void testDirected() {
		AdjacencyListDirectedGraph<Integer> dg = new AdjacencyListDirectedGraph<>();
		
		Integer v0 = 0;
		Integer v1 = 1;
		Integer v2 = 2;
		Integer v3 = 3;
		Integer v4 = 4;
		Integer v5 = 5;
		
		dg.addVertex(v0);
		dg.addVertex(v1);
		dg.addVertex(v2);
		dg.addVertex(v3);
		dg.addVertex(v4);
		dg.addVertex(v5);
		
		// Seite 3-37 richtung erfunden
		//dg.addEdge(v0, v1, 1);
		dg.addEdge(v1, v0, 1);
		dg.addEdge(v0, v4, 1);
		dg.addEdge(v1, v2, 1);
		dg.addEdge(v1, v4, 1);
		dg.addEdge(v2, v4, 1);
		dg.addEdge(v2, v3, 1);
		dg.addEdge(v3, v4, 1);
		dg.addEdge(v5, v4, 1);
		
		printAndSearch(dg, v1);
	}
	
	public static void testTopologSort() {
		AdjacencyListDirectedGraph<String> dg = new AdjacencyListDirectedGraph<>();
		
		String Struempfe = "Struempfe";
        String Schuhe = "Schuhe";
        String Hose  = "Hose";
        String Unterhose = "Unterhose";
        String Unterhemd = "Unterhemd";
        String Hemd = "Hemd";
        String Guertel = "Guertel";
        String Pullover = "Pullover";
        String Mantel = "Mantel";
        String Schal = "Schal";
        String Handschuhe = "Handschuhe";
        String Muetze = "Muetze";
        
        dg.addVertex(Struempfe);
        dg.addVertex(Schuhe);
        dg.addVertex(Hose);
        dg.addVertex(Unterhose);
        dg.addVertex(Unterhemd);
        dg.addVertex(Hemd);
        dg.addVertex(Guertel);
        dg.addVertex(Pullover);
        dg.addVertex(Mantel);
        dg.addVertex(Schal);
        dg.addVertex(Handschuhe);
        dg.addVertex(Muetze);
        
        
        dg.addEdge(Unterhemd, Hemd);
        dg.addEdge(Unterhose, Hose);
        dg.addEdge(Struempfe, Schuhe);
        dg.addEdge(Hemd, Hose);
        
        dg.addEdge(Hose, Guertel);
        dg.addEdge(Hose, Pullover);
        dg.addEdge(Hose, Schuhe);
        
        dg.addEdge(Pullover, Mantel);
        dg.addEdge(Guertel, Mantel);
        dg.addEdge(Schuhe, Mantel);
        
        dg.addEdge(Mantel, Handschuhe);
        dg.addEdge(Mantel, Schal);
        dg.addEdge(Mantel, Muetze);
        
        
        List<String> dressOrder = GraphTraversion.topologicalSort(dg);
        
        for (String c : dressOrder ) {
        	System.out.println(c);
        }
        
	}
	

}

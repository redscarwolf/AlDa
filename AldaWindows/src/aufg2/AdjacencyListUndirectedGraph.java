package aufg2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class AdjacencyListUndirectedGraph<V> implements UndirectedGraph<V> {
	
	// doppelte Hashmap
	// TODO nur Map
    HashMap<V, HashMap<V, Double>> data;

    public AdjacencyListUndirectedGraph() {
        data = new HashMap<V, HashMap<V, Double>>();
    }

    @Override
    public int getDegree(V v) {
    	// existiert der Knoten?
        doesVertexExist(v);
       
        // Anzahl der adjazenten Knoten
        // Anzahl der Elemente in 2er HashMap
        return data.get(v).size();
    }

    @Override
    public boolean addVertex(V v) {
        // fuer ruegabewert abfragen ob enthalten
        boolean returnValue = !data.containsKey(v);
        if (returnValue) {
        	// fuege Knoten in 1er HashMap ein
        	data.put(v, new HashMap<V, Double>());
        }
        return returnValue;
    }

    @Override
    public boolean addEdge(V v, V w) {
    	// fuegt Gewicht von 1.0 ein
        return addEdge(v, w, 1.0);
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
    	// existieren die Knoten?
    	doesVertexExist(v);
    	doesVertexExist(w);
    	// sind Knoten identisch?
    	// TODO equals oder ==
    	if (v.equals(w)) {
    		throw new IllegalArgumentException("Nodes are the same.");
    	}
    	
    	// teste bei einem Knoten ob Kante schon vorhanden ist
        boolean returnValue = data.get(v).containsValue(w);
        
        // fuege Kante bei beiden Knoten in 2e HashMap ein
        data.get(v).put(w, weight);
        data.get(w).put(v, weight);
        
        return returnValue;
    }

    @Override
    public boolean containsVertex(V v) {
    	// sucht in 1er HashMap
        return data.containsKey(v);
    }

    @Override
    public boolean containsEdge(V v, V w) {
    	// existieren die Knoten?
    	doesVertexExist(v);
    	doesVertexExist(w);
    	
        // teste bei einem Knoten ob Kante schon vorhanden ist
    	// teste ob in 2er HashMap andere Knoten enthalten ist
        return data.get(v).containsKey(w);
    }

    @Override
    public double getWeight(V v, V w) {
    	// TODO Sebi sagen, dass sonst 0 fehlt
    	
    	// existieren die Knoten?
    	doesVertexExist(v);
    	doesVertexExist(w);
    	
    	// wenn Kante vorhanden..
    	if (containsEdge(v, w)) {
    		// .. dann geb Gewicht zurueck ..
    		return data.get(v).get(w);
    	}
    	// .. ansonsten gebe Gewicht 0 zurueck
    	return 0;
    }

    @Override
    public int getNumberOfVertexes() {
    	// gebe Anzahl der Knoten in 1er hashMap zurueck
        return data.size();
    }

    @Override
    public int getNumberOfEdges() {
        int edges = 0;
        
        // data.values gibt eine Collection der Values zurueck
        for (HashMap<V, Double> i : data.values()) {
        	// lese groesse aller 2en HashMaps
            edges += i.size();
        }
        // hab doppelte Anzahl Kanten bei ungerichtetenGraph
        edges = edges / 2;
        return edges;
    }

    @Override
    public List<V> getVertexList() {
    	// bekomme zuerst eine Set von Keys
    	// die dann in eine Liste uebertragen werden
        return new LinkedList<V>(data.keySet());
    }
    
    
    @Override
    public List<Edge<V>> getEdgeList() {
        LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
        // gehe alle Knoten in 1er hashMap durch
        for (V v : data.keySet()) {
          	// gehe ueber alle verbundenen Knoten  in 2er HashMap
            for (V w : data.get(v).keySet()) {
            	if (!containsEdge(v, w, edgeList)) {
            		// wenn nicht in Liste dann hinzufuegen
            		double weight = data.get(v).get(w);
                    edgeList.add(new Edge<V>(v, w, weight));
            	}
            }
        }
        return edgeList;
    }
    
    
    private boolean containsEdge(V v, V w, LinkedList<Edge<V>> edgeList) {
    	
    	// schaue ob es Kante(w,v) schon gibt weil Kante(v,w) = Kante(w,v)
    	double weight = data.get(v).get(w);
    	Edge<V> edgeSister = new Edge<V>(w, v, weight);
    	
    	// ganze Liste durchsuchen
    	for (Edge<V> e : edgeList) {
    		if (e.equals(edgeSister)) {
    			// edgeSister in liste gefunden
    			return true;
    		}
    	}
    	// edgeSister in liste nicht gefunden
    	return false;
    }


    @Override
    public List<V> getAdjacentVertexList(V v) {
        doesVertexExist(v);
        
        // erzeuge eine Menge von adjazentz Knoten
        Set<V> nodes = data.get(v).keySet();
        // schreib sie in eine Liste
        return new LinkedList<V>(nodes);
    }

    @Override
    public List<Edge<V>> getIncidentEdgeList(V v) {
        doesVertexExist(v);

        // Add all Edges to List
        LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
        
        // erzeuge Edges und fuege sie zu edgeList
        for (V w : data.get(v).keySet()) {
        	double weight = data.get(v).get(w);
            edgeList.add(new Edge<V>(v, w, weight));
        }
        return edgeList;
    }



    private void doesVertexExist(V v) {
        // ueberpruefe ob Knoten im Graph vorhanden
        if (!data.containsKey(v)) {
            throw new IllegalArgumentException("Node does not exist.");
        }
    }

    // TODO loeschen
//    public List<Edge<V>> getEdgeList2() {
//    	LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
//    	// Iterate over nodes "v"
//    	for (V v : data.keySet()) {
//    		// Iterate over connected nodes "i"
//    		nextv:
//    			for (V i : data.get(v).keySet()) {
//    				// Handle double existing Edges (o,i) = (i,o)
//    				// Compare Edge
//    				Edge<V> compareEdge = new Edge<V>(i, v, data.get(v).get(i));
//    				// Iterate over List and check if Edge already exists
//    				for (Edge<V> e : edgeList) {
//    					if (e.equals(compareEdge)) {
//    						continue nextv;
//    					}
//    				}
//    				edgeList.add(new Edge<V>(v, i, data.get(v).get(i)));
//    			}
//    	}
//    	return edgeList;
//    }
    
    // TODO entfernen   
    
//    /*
//     * Private Methods for check if Arguments are valid
//     */
//    private void checkArguments(V v) {
//    	// Test if Nodes != NULL
//    	if (v == null) {
//    		throw new IllegalArgumentException("Node is NULL.");
//    	}
//    }
    
 // TODO entfernen   
//    private void doesVertexExist(V v, V w) {
//        // Test if both Nodes != NULL
//        if (v == null && w == null) {
//            throw new IllegalArgumentException("Node is NULL.");
//        }
//        // Test if both Nodes exist
//        if (!data.containsKey(v) || !data.containsKey(w)) {
//            throw new IllegalArgumentException("Node does not exist.");
//        }
//    }
}

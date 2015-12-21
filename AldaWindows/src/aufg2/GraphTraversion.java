package aufg2;


import java.util.*;


public class GraphTraversion {

	// ## Tiefensuche rekursiv ##########################################
	
    public static <V> List<V> depthFirstSearch(Graph<V> g, V v) {

        // initialize variables
    	// result
        LinkedList<V> searchList = new LinkedList<V>();
        
        // visited not visted HashMap
        HashMap<V, Boolean> visited = new HashMap<V, Boolean>();
        
        // initialize in visited each Vertex with false
        for (V vb : g.getVertexList()) {
            visited.put(vb, false);
        }

        depthFirstSearch(v, g, visited, searchList);
        return searchList;
    }

    private static <V> void depthFirstSearch(V v, Graph<V> g,
    										 HashMap<V, Boolean> visited,
    										 List<V> searchList) {
        
    	// set current node to be visited
    	visited.put(v, true);

        // save v by adding to searchList
        searchList.add(v);

        for (V w : g.getAdjacentVertexList(v)) {
            // looking over all adjacents if they are already visited
            if (!visited.get(w).booleanValue()) {
            	// w not jet visited, search in w
                depthFirstSearch(w, g, visited, searchList);
            }
        }
    }

    // ## breitensuche ##############################################################
    
    public static <V> List<V> breadthFirstSearch(Graph<V> g, V v) {

    	// initialize variables
    	// return
        LinkedList<V> searchList = new LinkedList<V>();
        
        // visited and not visited Map
        HashMap<V, Boolean> visited = new HashMap<V, Boolean>();

        // initialize visited with false
        for (V vb : g.getVertexList()) {
            visited.put(vb, false);
        }

        breadthFirstSearch(v, g, visited, searchList);
        return searchList;
    }

    private static <V> void breadthFirstSearch(V v, Graph<V> g,
    										   HashMap<V, Boolean> visited,
    										   List<V> searchList) {
        // initialize Queue of vertex
        Queue<V> q = new ArrayDeque<>();
        q.add(v);

        while (!q.isEmpty()) {
            v = q.remove();
            
            // check if visted
            if (visited.get(v).booleanValue()) {
                continue;
            }
            // set current vertex visited
            visited.put(v, true);

            // save vertex by adding to searchList
            searchList.add(v);

            // Add all adjazented not visited vertexes to Queue
            for (V w : g.getAdjacentVertexList(v)) {
            	
                if (!visited.get(w).booleanValue()) {
                	// not visited added
                    q.add(w);
                }
            }
        }
    }
    
    // ## topologische Sortierung ###############################################

    public static <V> List<V> topologicalSort(DirectedGraph<V> g) {

        // Init vars
    	// topological sorted List ts
        List<V> ts = new LinkedList<V>();
        //save each vertex and its count of not visited predecessors in this list
        HashMap<V, Integer> inDegree = new HashMap<V, Integer>();
        Queue<V> q = new ArrayDeque<V>();

        // init inDegree and q
        for (V v : g.getVertexList()) {
        	// for each v add the count of not visited(at beginning all) predecessors
            inDegree.put(v, g.getInDegree(v));
            
            if (inDegree.get(v) == 0) {
            	// adds the vertex with no predecessor, the start vertex
                q.add(v);
            }
        }

        // do as long as you have vertex in q
        while (!q.isEmpty()) {
            V v = q.remove();
            ts.add(v);
            
            // go through all successors
            for (V w : g.getSuccessorVertexList(v)) {
                int wValue = inDegree.get(w);
                //decrease value
                inDegree.put(w, --wValue);
                if (wValue == 0) {
                	// vertex has no open predecessors, can be added to q
                    q.add(w);
                }   
            }
        }

        // Check if graph has cycle
        if (ts.size() != g.getNumberOfVertexes()) {
            return null;
        } else {
            return ts;
        }
    }
}

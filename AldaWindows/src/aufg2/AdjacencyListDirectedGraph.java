package aufg2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
	// vorgaenger
    HashMap<V, HashMap<V, Double>> in;
    // nachfolger
    HashMap<V, HashMap<V, Double>> out;

    public AdjacencyListDirectedGraph() {
        in = new HashMap<V, HashMap<V, Double>>();
        out = new HashMap<V, HashMap<V, Double>>();
    }

    @Override
    public int getInDegree(V o) {
        // Test if Nodes != NULL
        checkArgumentsExist(o);

        return in.get(o).size();
    }

    @Override
    public int getOutDegree(V o) {
        // Test if Nodes != NULL
        checkArgumentsExist(o);

        return out.get(o).size();
    }

    @Override
    public List<V> getPredecessorVertexList(V o) {
        // Test if Nodes != NULL
        checkArgumentsExist(o);

        return new LinkedList<V>(in.get(o).keySet());
    }

    @Override
    public List<V> getSuccessorVertexList(V v) {
        // Test if Nodes != NULL
        checkArgumentsExist(v);

        return getAdjacentVertexList(v);
    }

    @Override
    public List<Edge<V>> getOutgoingEdgeList(V o) {
        // Test if Nodes != NULL
        checkArgumentsExist(o);

        return getIncidentEdgeList(o);
    }

    @Override
    public List<Edge<V>> getIncomingEdgeList(V o) {
        // Add Edges to List
        LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
        for (V i : in.get(o).keySet()) {
            edgeList.add(new Edge<V>(o, i, in.get(o).get(i)));
        }
        return edgeList;
    }

    @Override
    public boolean addVertex(V o) {
        // Test if Nodes != NULL
        checkArguments(o);

        boolean returnValue = out.containsKey(o);
        in.put(o, new HashMap<V, Double>());
        out.put(o, new HashMap<V, Double>());
        return returnValue;
    }

    @Override
    public boolean addEdge(V o, V w) {
        return addEdge(o, w, 1);
    }

    @Override
    public boolean addEdge(V o, V w, double weight) {
        checkArgumentsExist(o, w);
        // Test if both Nodes are identical
        if (o.equals(w)) {
            throw new IllegalArgumentException("Nodes are identical.");
        }
        // Insert Edge to both Nodes
        boolean returnValue = out.get(o).containsValue(w);
        out.get(o).put(w, weight);
        in.get(w).put(o, weight);
        return returnValue;
    }

    @Override
    public boolean containsVertex(V o) {
        // Test if Nodes != NULL
        checkArguments(o);

        return (out.containsKey(o) || in.containsKey(o));
    }

    @Override
    public boolean containsEdge(V o, V w) {
        // Test if both Nodes != NULL and exist
        checkArgumentsExist(o, w);
        // Return if there is an Edges
        return out.get(o).containsKey(w);
    }

    @Override
    public double getWeight(V o, V w) {
        checkArgumentsExist(o, w);

        // Return weight
        return out.get(o).get(w);
    }

    @Override
    public int getNumberOfVertexes() {
        return out.size();
    }

    @Override
    public int getNumberOfEdges() {
        // Iterate over out and sum
        int edges = 0;
        for (HashMap<V, Double> i : out.values()) {
            edges += i.size();
        }
        return edges;
    }

    @Override
    public List<V> getVertexList() {
        return new LinkedList<V>(out.keySet());
    }

    @Override
    public List<Edge<V>> getEdgeList() {
        LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
        // Iterate over nodes "obj"
        for (V obj : out.keySet()) {
            // Iterate over connected nodes "i"
            for (V i : out.get(obj).keySet()) {
                edgeList.add(new Edge<V>(obj, i, out.get(obj).get(i)));
            }
        }
        return edgeList;
    }

    @Override
    public List<V> getAdjacentVertexList(V o) {
        // Test if Nodes != NULL
        checkArgumentsExist(o);

        return new LinkedList<V>(out.get(o).keySet());
    }

    @Override
    public List<Edge<V>> getIncidentEdgeList(V o) {
        // Add Edges to List
        LinkedList<Edge<V>> edgeList = new LinkedList<Edge<V>>();
        for (V i : out.get(o).keySet()) {
            edgeList.add(new Edge<V>(o, i, out.get(o).get(i)));
        }
        return edgeList;
    }

    /*
     * Private Methods for check if Arguments are valid
     */
    private void checkArguments(V o) {
        // Test if Nodes != NULL
        if (o == null) {
            throw new IllegalArgumentException("Node is NULL.");
        }
    }

    private void checkArgumentsExist(V o) {
        // Test if Nodes != NULL
        if (o == null) {
            throw new IllegalArgumentException("Node is NULL.");
        }
        // Test if both Nodes exist
        if (!out.containsKey(o)) {
            throw new IllegalArgumentException("Node does not exist.");
        }
    }

    private void checkArgumentsExist(V o, V w) {
        // Test if both Nodes != NULL
        if (o == null && w == null) {
            throw new IllegalArgumentException("Node is NULL.");
        }
        // Test if both Nodes exist
        if (!out.containsKey(o) || !out.containsKey(w)) {
            throw new IllegalArgumentException("Node does not exist.");
        }
    }
}

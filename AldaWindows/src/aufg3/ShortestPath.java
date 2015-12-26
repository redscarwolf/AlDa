package aufg3;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import aufg2.*;
import sim.SYSimulation;

public class ShortestPath<V> {
	
	private HashMap<V, Double> d;
	private HashMap<V, V> p;
	private Graph<V> disGraph;
	private V g = null;
	private Heuristic<V> heuristic;
	private SYSimulation sim = null;
	
	public ShortestPath(Graph<V> disGraph, Heuristic<V> h) {
		// ensure pos weight (Distanzgraph)
		// TODO
		this.disGraph = disGraph;
		this.d = new HashMap<>();
		this.p = new HashMap<>();
		
		this.heuristic = h;
	}
	
	private boolean shortestPath_A(V s,
								V z,
								Graph<V> graph,
								HashMap<V, Double> d,
								HashMap<V, V> p) {
		
		if (!graph.containsVertex(s)) {
			throw new IllegalArgumentException("ERROR: start Vertex s is not in g.");
		}
		if (!graph.containsVertex(z)) {
			throw new IllegalArgumentException("ERROR: target Vertex z is not in g.");
		}
		
		
		PriorityList<V> kl = new PriorityList<>();
		
		for (V vb : graph.getVertexList()) {
			d.put(vb, 999.9);
			p.put(vb, null);
		}
		
		if (!d.containsKey(s))  // check for safety not necessary
			throw new IllegalArgumentException("ERROR: start Vertex s is not in d.");
		d.put(s, 0.0);
		
		kl.insert(s, 0.0);
		
		while(!kl.isEmty()) {
			V v = kl.deleteMin(heuristic, z);  // get deleted Vertex v with least distance
			if (sim != null) sim.visitStation((Integer) v, Color.blue);
			if (v == z) { // reached target Vertex
				return true;
			}
			
			for (V w : graph.getAdjacentVertexList(v)) {
				if (sim != null) sim.drive((Integer) v, (Integer) w, Color.cyan);
				
				if (d.get(w) == 999.9) {  // w not been visited add to kl
					kl.insert(w, 999.9);  // add w and the distance from v to w, to the PriorityList
				}
				if (d.get(v) + graph.getWeight(v, w) < d.get(w)) {
					// update p and d
					p.put(w, v);
					d.put(w, d.get(v) + graph.getWeight(v, w));
					// update PriorityList kl
					kl.change(w, d.get(w));  // get just new added distance and add it to PriorityList
					graph.getWeight(v, w);
				}
			}
		}
		return false;
	}
	
	private void shortestPath(V s,
							 Graph<V> graph,
							 HashMap<V, Double> d,
							 HashMap<V, V> p) {
		
		if (!graph.containsVertex(s)) {
			throw new IllegalArgumentException("ERROR: start Vertex s is not in g.");
		}
		
		
		PriorityList<V> kl = new PriorityList<>();
		
        for (V vb : graph.getVertexList()) {
            d.put(vb, 999.9);
            p.put(vb, null);
        }
        
        if (!d.containsKey(s))  // check for safety not necessary
        	throw new IllegalArgumentException("ERROR: start Vertex s is not in d.");
        d.put(s, 0.0);
        
        kl.insert(s, 0.0);
        
        while(!kl.isEmty()) {
        	V v = kl.deleteMin();  // get deleted Vertex v with least distance
        	if (sim != null) sim.visitStation((Integer) v, Color.blue);
        	
        	for (V w : graph.getAdjacentVertexList(v)) {
        		if (sim != null) sim.drive((Integer) v, (Integer) w, Color.cyan);
        		
        		if (d.get(w) == 999.9) {  // w not been visited add to kl
        			kl.insert(w, 999.9);  // add w and the distance from v to w, to the PriorityList
        		}
        		if (d.get(v) + graph.getWeight(v, w) < d.get(w)) {
        			// update p and d
        			p.put(w, v);
        			d.put(w, d.get(v) + graph.getWeight(v, w));
        			// update PriorityList kl
        			kl.change(w, d.get(w));  // get just new added distance and add it to PriorityList
        			graph.getWeight(v, w);
        		}
        	}
        }
	}
	
	
	public double getDistance() {
		return d.get(this.g);
	}

	public List<V> getShortestPath() {
		LinkedList<V> shPathList = new LinkedList<>();
		V pointer = this.g; // aim vertex
		shPathList.add(pointer); // add aim vertex
		
		while (p.get(pointer) != null) {  // parent != null
			shPathList.addFirst(p.get(pointer));
			pointer = p.get(pointer);
		}
		return shPathList;
	}
 
	public void searchShortestPath(V s, V g) {
		// save g for getDistance and getShortestPath
		this.g = g;
		
		
		// get table with d and p for source s (single-source shortest-path)
		shortestPath(s, this.disGraph, this.d, this.p);
		
	}

	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

}

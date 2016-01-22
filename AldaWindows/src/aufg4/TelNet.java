package aufg4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class TelNet {
	
	// input parameters
	private final int lbg;
	private PriorityQueue<TelVerbindung> edges = new PriorityQueue<>();
	private HashMap<TelKnoten, Integer> vertexMap = new HashMap<>();
	// Result
	private List<TelVerbindung> minSpanTree;
	
	public TelNet(int lbg) {
		this.lbg = lbg;
	}
	
	public static void main(String[] args) {
		showTest3();
		showTest4();
	}
	
	private static void showTest3() {
		// test to 3. network with 7 vertexes
		int lbg_test = 7;  // 5 is last possible
		
		TelNet telnetzwerk7Knoten = new TelNet(lbg_test);
		telnetzwerk7Knoten.addTelKnoten(1,1);
		telnetzwerk7Knoten.addTelKnoten(3,1);
		telnetzwerk7Knoten.addTelKnoten(4,2);
		telnetzwerk7Knoten.addTelKnoten(3,4);
		telnetzwerk7Knoten.addTelKnoten(7,5);
		telnetzwerk7Knoten.addTelKnoten(2,6);
		telnetzwerk7Knoten.addTelKnoten(4,7);
		System.out.printf(telnetzwerk7Knoten.toString());
		
		boolean success = telnetzwerk7Knoten.computeOptTelNet();
		List<TelVerbindung> list = telnetzwerk7Knoten.getOptTelNet();
		System.out.printf("%nminSpanTree(%s): %s%n",success, list );
		System.out.printf("Gesammt Kosten:%s%n%n", telnetzwerk7Knoten.getOptTelNetKosten());
		System.out.printf(telnetzwerk7Knoten.toString());
	}
	
	private static void showTest4() {
		// test to 4. network with 1000 vertexes
		int lbg_test2 = 100;
		int n = 1000;
		int xMax = 1000;
		int yMax = 1000;
		TelNet telnetzwerk1000Knoten = new TelNet(lbg_test2);
		telnetzwerk1000Knoten.generateRandomTelNet(n, xMax, yMax);
		telnetzwerk1000Knoten.computeOptTelNet();
		System.out.printf(telnetzwerk1000Knoten.toString());
		telnetzwerk1000Knoten.drawOptTelNet(xMax, yMax);
	}
	
	public boolean addTelKnoten(int x, int y) {
		//does vertex with the coordinates already exist?
		for (TelKnoten v : vertexMap.keySet()) {
			if (v.x == x && v.y == y) {
				return false; 
			}
		}
		
		// add vertex
		TelKnoten vertex = new TelKnoten(x, y);
		generateEdges(vertex);  // all possible edges to vertex
		vertexMap.put(vertex, size());  // save Number
		return true;
	}
	
	public boolean computeOptTelNet() {
		UnionFind forest = new UnionFind(size());
		minSpanTree = new LinkedList<>();  // create a minSpanTree to save results
		
		while (forest.size() != 1 && ! edges.isEmpty() ) {
			TelVerbindung e = edges.poll();  // get edge with lowest cost
			int t1 = forest.find(vertexMap.get(e.u));  // get startVertex u of edge e and search its number in vertexMap ..
			int t2 = forest.find(vertexMap.get(e.v));  // .. than find the Tree for the Vertex
			if (t1 != t2) {
				forest.union(t1,t2);
				minSpanTree.add(e);
			}
		}
		
		if (edges.isEmpty() && forest.size() != 1) {
			minSpanTree.clear();  // remove minSpanTree results, because they are wrong
			return false;
		} else {
			return true;
		}
	}
	
	public List<TelVerbindung> getOptTelNet() throws IllegalStateException {
		if (minSpanTree == null) {  // check if computeOptTelNet already has been run and created a minSpanTree
			throw new IllegalStateException();
		}
		return minSpanTree;
	}
	
	public int getOptTelNetKosten() throws java.lang.IllegalStateException {
		if (minSpanTree == null) {  // check if computeOptTelNet already has been run and created a minSpanTree
			throw new IllegalStateException();
		}
		// if Empty return Infinity
		if (minSpanTree.isEmpty()) {
			return (int) Double.POSITIVE_INFINITY;
		}
		// get all costs in sum
		int sum = 0;
		for (TelVerbindung e : minSpanTree) {
			sum += e.c;
		}
		return sum;
	}

	public void generateRandomTelNet(int n, int xMax, int yMax) {
		if (n <= 0 || xMax <= 0 || yMax <= 0) {
            throw new IllegalArgumentException();
        }
		
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			int x = random.nextInt(xMax + 1);  // [0, xMax]
			int y = random.nextInt(yMax + 1);  // [0, yMax]
			
			// Add vertex and if exist create a new one
            if (!addTelKnoten(x, y)) {
                i--;
            }
		}
	}

	public void drawOptTelNet(int xMax, int yMax) throws java.lang.IllegalStateException {
		if (minSpanTree == null) {  // check if computeOptTelNet already has been run and created a minSpanTree
			throw new IllegalStateException();
		}
		//draw
		StdDraw.clear();
		StdDraw.setXscale(0,xMax);
		StdDraw.setYscale(0,yMax);
        StdDraw.setPenRadius(0.008);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.point(1000, 1100);

        // Draw vertex
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (TelKnoten v: vertexMap.keySet()) {
        	double x = v.x;
        	double y = v.y;
            StdDraw.point(x, y);
            StdDraw.show(0);
        }

        // Draw edges
        StdDraw.setPenRadius(0.0008);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (TelVerbindung e : minSpanTree) {
            // Draw Y part
        	double x0 = e.u.x;
        	double y0 = e.u.y;
        	double x1 = e.u.x;
        	double y1 = e.v.y;
            StdDraw.line(x0, y0, x1, y1);
            // Draw X part
            double x2 = e.u.x;
            double y2 = e.v.y;
            double x3 = e.v.x;
            double y3 = e.v.y;
            StdDraw.line(x2, y2, x3, y3);
            StdDraw.show(1);
        }
        StdDraw.show();
	}
	
	
	public int size() {
		return vertexMap.size();
	}
	
	@Override
	public String toString() {
		// TODO
		return "TelNet[lbg=" + lbg + "]:%n" +
				"vertexMap= " + vertexMap + "%n" +
				"edges= " + edges + "%n" +
				"minSpanTree= " + minSpanTree + "%n";
	}
	
	private void generateEdges(TelKnoten u) {
		if (vertexMap.isEmpty()) {
			return;  // do nothing
		}
		
		for(TelKnoten v : vertexMap.keySet()) {  // generate for u every edge to existing v
			int dist = manhattenDistance(u, v);
			if (dist <= lbg) {  // edge is short enough
				edges.add(new TelVerbindung(u, v, dist));
			}
		}
	}
	
	private int manhattenDistance(TelKnoten u, TelKnoten v) {
		if (u == null || v == null) {
			throw new IllegalArgumentException();
		}
		
		return Math.abs(u.x - v.x) + Math.abs(u.y - v.y);
	}

	// TODO nicht benötigt
	public PriorityQueue<TelVerbindung> getEdges() {
		PriorityQueue<TelVerbindung> prio = new PriorityQueue<>();
		
		// generate each possible edge, except edges to itself, and edges that are over lbg
		for(TelKnoten u : vertexMap.keySet()) {
            for(TelKnoten v : vertexMap.keySet()) {
                if (u == v) {  // edge to itself do nothing
                    continue;
                }

                int dist = manhattenDistance(u, v);
                if (dist <= lbg) {  // edge is short enough
                	edges.add(new TelVerbindung(u, v, dist));
                }
            }
        }
		return prio;
	}

    
    // TODO nicht benötigt
    public void test2() {
		TelNet telnetzwerk0 = new TelNet(4);
		telnetzwerk0.addTelKnoten(1,1);
		
		telnetzwerk0.addTelKnoten(2,3);

		telnetzwerk0.addTelKnoten(2,3);

		telnetzwerk0.addTelKnoten(0,0);
		telnetzwerk0.addTelKnoten(3,3);
		telnetzwerk0.addTelKnoten(4,4);

		
	}
    
	// TODO nicht benötigt
	public void testPrioQueue() {
		LinkedList<TelVerbindung> list = new LinkedList<>();
		TelKnoten u = new TelKnoten(0, 0);
		TelKnoten v = new TelKnoten(1, 1);
		TelKnoten w = new TelKnoten(2, 2);
		TelKnoten x = new TelKnoten(3, 3);
		
		list.add(new TelVerbindung(u, v, 3));
		list.add(new TelVerbindung(u, w, 1));
		list.add(new TelVerbindung(x, v, 8));
		PriorityQueue<TelVerbindung> prio = new PriorityQueue<>(list);
		System.out.println(list);
		System.out.println(prio);
		prio.poll();
		System.out.println(prio);
		prio.add(new TelVerbindung(x, w, 2));
		System.out.println(prio);
		prio.poll();
		System.out.println(prio);
		//prio.poll();
		System.out.println(prio);
		//prio.poll();
		System.out.println(prio);
		TelVerbindung asd = prio.poll();
		System.out.printf("%s %s %s", prio, asd, prio.isEmpty());
	}
}

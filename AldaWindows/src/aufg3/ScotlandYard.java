package aufg3;

import aufg2.*;

import java.io.FileNotFoundException;

import sim.SYSimulation;

import java.awt.Color;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ScotlandYard {
	private static final int TAXI_WEIGHT = 2;
	private static final int BUS_WEIGHT = 3;
	private static final int UBAHN_WEIGHT = 5;

	/**
	 * Fabrikmethode zur Erzeugung eines Graphen für den Scotland-Yard-Spielplan.
	 * <p>
	 * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
	 * Für die Verbindungen werden folgende Gewichte angenommen:
	 * U-Bahn = 5, Taxi = 2 und Bus = 3.
	 * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
	 * wird das billigste Beförderungsmittel gewählt.
	 * @return Graph für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static Graph<Integer> getGraph() throws FileNotFoundException {

		Graph<Integer> sy_graph = new AdjacencyListUndirectedGraph<Integer>();
		//TODO: windows-linux
		// Scanner in = new Scanner(new File("src/aufg3/ScotlandYard_Kanten.txt")); // Linux
		Scanner in = new Scanner(new File("src\\aufg3\\ScotlandYard_Kanten.txt")); // Windows
		
		while (in.hasNextLine()) {
			int v,w, weight;
			String transport;
			
			// get data
			v = in.nextInt();
			w = in.nextInt();
			transport = in.next();
			switch (transport) {
			case "Taxi":
				weight = TAXI_WEIGHT;
				break;
			case "Bus":
				weight = BUS_WEIGHT;
				break;
			case "UBahn":
				weight = UBAHN_WEIGHT;
				break;
			default:
				weight = 1;
			}
			System.out.printf("%d %d %d %n",v, w, weight);
			
			// process data
			sy_graph.addVertex(v);
			sy_graph.addVertex(w);
			double oldWeight = sy_graph.getWeight(v, w);
			if (oldWeight == 0 || oldWeight > weight) { // no Edge OR new weight is smaller
				sy_graph.addEdge(v, w, weight);
			}
		}
		// show all edges
   		List<Edge<Integer>> edgeList = sy_graph.getEdgeList();
   		System.out.println(edgeList);
   		
		in.close();
		return sy_graph;
	}


	/**
	 * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
	 * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
	 * Die Heuristik wird für A* benötigt.
	 * <p>
	 * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
	 * ScotlandYard_Knoten.txt in eine Map ein.
	 * Die zurückgelieferte Heuristik-Funktion estimatedCost
	 * berechnet einen skalierten Euklidischen Abstand.
	 * @return Heuristik für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
		return new ScotlandYardHeuristic();
	}

	/**
	 * Scotland-Yard Anwendung.
	 * @param args wird nicht verewendet.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Graph<Integer> syGraph = getGraph();
		Heuristic<Integer> syHeuristic = getHeuristic();
		ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph, syHeuristic);
		
		sySp.searchShortestPath(65,157);
		System.out.println("Distance = " + sySp.getDistance()); // 9.0

		sySp.searchShortestPath(1,175);
		System.out.println("Distance = " + sySp.getDistance()); // 25.0

		sySp.searchShortestPath(1,173);
		System.out.println("Distance = " + sySp.getDistance()); // 22.0


		// Simulation
		SYSimulation sim;
		try {
			sim = new SYSimulation();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		sySp.setSimulator(sim);
		sim.startSequence("Shortest path from 1 to 173");

		//sySp.searchShortestPath(65,157); // 9.0
		//sySp.searchShortestPath(1,175); //25.0
		
		sySp.searchShortestPath(1,173); //22.0
		System.out.println("Distance = " + sySp.getDistance());
		List<Integer> sp = sySp.getShortestPath();

		int a = -1;
		for (int b : sp) {
			if (a != -1)
			sim.drive(a, b, Color.RED.darker());
			sim.visitStation(b);
			a = b;
		}
		
		sim.stopSequence();

    }
}

class ScotlandYardHeuristic implements Heuristic<Integer> {
	Map<Integer, Entry> vertexCoordinates = new HashMap<Integer, Entry>();

	public ScotlandYardHeuristic() throws FileNotFoundException {
		//TODO: windows-linux
		// Scanner in = new Scanner(new File("src/aufg3/ScotlandYard_Knoten.txt")); // Linux
		Scanner in = new Scanner(new File("src\\aufg3\\ScotlandYard_Knoten.txt")); // Windows
		
		while (in.hasNextLine()) {
			int v;
			double x, y;
			
			// get data
			v = in.nextInt();
			x = in.nextDouble();
			y = in.nextDouble();
			// System.out.printf("%d %f %f %n",v, x, y);
			
			// save data in map
			vertexCoordinates.put(v, new Entry(x, y));
		}
		in.close();
	}
	
	private static class Entry {
		double x;
		double y;
		
		public Entry(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
	@Override
	public double estimatedCost(Integer u, Integer v) {
		// Checks
		if (!vertexCoordinates.containsKey(u)) {
			throw new IllegalArgumentException("ERROR: Vertex u is not in vertexCoordinates-Map.");
		}
		if (!vertexCoordinates.containsKey(v)) {
			throw new IllegalArgumentException("ERROR: Vertex v is not in vertexCoordinates-Map.");
		}
		
		// Berechne Euklidischen Abstand zwischen
		// Knoten u und v in Pixeleinheiten.
		double x1, x2, y1, y2, distance;
		x1 = vertexCoordinates.get(u).x;
		y1 = vertexCoordinates.get(u).y;
		
		x2 = vertexCoordinates.get(v).y;
		y2 = vertexCoordinates.get(v).y;
		
		// Pythagoras Theorem
		distance = Math.sqrt(Math.pow(x2 -x1, 2) + Math.pow(y2 -y1, 2));
		System.out.printf("Reale Distanz: %f%n", distance);
		
		// Skaliere Rueckgabewert mit Faktor 1/10 bis 1/80.
		distance /= 10.0;
		System.out.printf("Gewichtete Distanz: %f%n", distance);
		return distance;
	}
}


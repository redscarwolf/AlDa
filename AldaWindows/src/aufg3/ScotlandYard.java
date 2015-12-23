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
		Scanner in = new Scanner(new File("src\\aufg3\\ScotlandYard_KantenTest.txt")); // Windows
		
		while (in.hasNextLine()) {
			int v,w, weight;
			String transport;
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
		}
		System.out.println("ende");
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
		ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph,syHeuristic);
		
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
	// ...

	public ScotlandYardHeuristic() throws FileNotFoundException {
		// Lese Koordinaten von ScotlandYard_Knoten.txt in eine Map.
		
		// ...
	}

	public double estimatedCost(Integer u, Integer v) {
		// Berechne Euklidischen Abstand zwischen
		// Knoten u und v in Pixeleinheiten.
		// Skaliere Rückgabewert mit Faktor 1/10 bis 1/80.
		
		// ...
	}
}


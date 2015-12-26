package aufg3;

public interface Heuristic<T> {
	
	/**
	 * Erhalte Euklidischen Abstand zwischen u und v
	 * @param u Startknoten
	 * @param v Zielknoten
	 * @return Euklidischer Abstand
	 */
	public double estimatedCost(Integer u, Integer v); 
}

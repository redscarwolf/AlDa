// O. Bittel;
// 18.10.2011

package aufg2;
import java.util.List;

/**
 * Graph mit gerichteten Kanten.
 * Es ist zu beachten, dass g.addEdge(v,w) 
 * eine gerichtete Kante von v nach w einfügt.
 * @author Oliver Bittel
 * @since 21.10.2011
 * @param <V> Knotentyp.
 */
public interface DirectedGraph<V> extends Graph<V> {

    /**
     * Liefert Eingangsgrad des Knotens v zurück.
     * Das ist die Anzahl der Kanten mit Zielknoten v.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knoteneingangsgrad
     */
    int getInDegree(V v);

    /**
     * Liefert Ausgangsgrad des Knotens v zurück.
     * Das ist die Anzahl der Kanten mit Quellknoten v.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenausgangsgrad
     */
    int getOutDegree(V v);

    /**
     * Liefert die Liste aller Vorgängerknoten zu v zurück.
     * Das sind alle die Knoten, von denen eine Kante zu v führt.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenliste
     */
    List<V> getPredecessorVertexList(V v);

    /**
     * Liefert die Liste aller Nachfolgerknoten zu v zurück. 
     * Das sind alle die Knoten, zu denen eine Kante von v führt.
     * Methode leistet dasselbe wie
     * {@link Graph#getAdjacentVertexList(java.lang.Object) getAdjacentVertexList}
     * und ist aufgrund des bequemeren Namens eingführt.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenliste
     */
    List<V> getSuccessorVertexList(V v);

    /**
     * Liefert die Liste aller von Knoten v wegführenden Kanten.
     * Methode leistet dasselbe wie
     * {@link Graph#getIncidentEdgeList(java.lang.Object) getIncidentEdgeList}
     * und ist aufgrund des bequemeren Namens eingeführt.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Kantenliste
     */
    List<Edge<V>> getOutgoingEdgeList(V v);

    /**
     * Liefert die Liste aller zu Knoten v hinführenden Kanten.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Kantenliste
     */
    List<Edge<V>> getIncomingEdgeList(V v);
}

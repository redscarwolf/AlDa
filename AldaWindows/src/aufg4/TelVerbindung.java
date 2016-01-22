package aufg4;

public class TelVerbindung implements Comparable<TelVerbindung> {
	
	public final TelKnoten u;  // start vertex
	public final TelKnoten v;  // end vertex
	public final int c;  // costs
	
	public TelVerbindung(TelKnoten u, TelKnoten v, int c) {
		this.u = u;
		this.v = v;
		this.c = c;
	}

	@Override
	public String toString() {
		return u + "-" + c + "-" + v ;
	}

	@Override
	public int compareTo(TelVerbindung edge) {
	    if (edge == null) {
            throw new IllegalArgumentException();
        }
	    
		if (c < edge.c) {
			return -1;
		} else if (c == edge.c) {
			return 0;
		} else {
			return 1;
		}
	}
	
}

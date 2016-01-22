package aufg4;

import java.util.Arrays;

public class UnionFind {
	
	int[] p;  // partition
	
	public UnionFind(int n) {
		p = new int[n];
		Arrays.fill(p, -1);
		// or:
//		for(int e = 0; e < n; e++) {
//			p[e] = -1;
//		}
	}
	
	public String toStringP() {
		return Arrays.toString(p);
	}
	
	public static void main(java.lang.String[] args) {
		// TESTs
		// init
		UnionFind forest = new UnionFind(7);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// union
		forest.union(0, 1);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// find
		int t1 = forest.find(0);		
		int t2 = forest.find(0);		
		System.out.println("Tree1= " + t1);
		System.out.println("Tree2= " + t2);
		
		// union with the same representant, has no change
		forest.union(0, 0);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// union2
		forest.union(0, 2);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// union3
		forest.union(3, 4);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// union4
		forest.union(3, 0);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		forest.union(5, 6);
		forest.union(3, 5);
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
		// find2
		int t3 = forest.find(5);		
		System.out.println("Tree3= " + t3); // all are in Tree = 3
		System.out.printf("%s Size:%d%n", forest.toStringP(), forest.size());
		
	}
	
	public int find(int e) {
		if (p[e] <= -1) // e is root
			return e;
		else
			return find(p[e]); 
	}
	
	public void union(int s1, int s2) {  // unionByHeight
		
		if (p[s1] >= 0 || p[s2] >= 0) { // check if s1 and s2 are representative
			return;
		}
		if (s1 == s2) {  // check if s1 and s2 are not the same set
			return;
		}
		
		if ( -p[s1] < -p[s2] )       // (1) height s1 < height s2
			p[s1] = s2;
		else {                       // (2) height s1 >= height s2
			if ( -p[s1] == -p[s2] )  // (3) height s1 == height s2
				p[s1]--; // increase height
			p[s2] = s1;
		}
	}
	
	public int size() {  // number of sets in p | or number of roots in p
		int size = 0;
		for (int e : p) {
            if (e <= -1) {  // Check if root
                size++;
            }
        }
		return size;
	}
}
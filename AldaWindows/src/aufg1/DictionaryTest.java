package aufg1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DictionaryTest {
	
	public static void main(String[] args) {
		
		List<Dictionary<String, String>> dictList = Arrays.asList(
				new SortedArrayDictionary<String,String>(),
				new HashDictionary<String,String>(7),				
				new TreeDictionary<String,String>(),
				new MapDictionary<String,String>(new HashMap<String,String>())		
		);
				
		for (Dictionary<String, String> dict : dictList) {
			System.out.println("teste " + dict.getClass());
			System.out.println(dict.insert("gehen", "go") == null);
			System.out.println(dict.insert("gehen", "walk").equals("go"));
			System.out.println(dict.search("gehen").equals("walk"));
			System.out.println(dict.remove("gehen").equals("walk"));
			System.out.println(dict.remove("gehen") == null);
			//other tests
			System.out.println("OTHER #########");
			System.out.println(dict.insert("bleiben", "stay") == null);
			System.out.println(dict.insert("bleiben", "bullshit").equals("stay"));
			System.out.println(dict.insert("auto", "car") == null);
			System.out.println(dict.insert("fahrrad", "bike") == null);
			System.out.println(dict.search("fahrrad").equals("bike"));
			System.out.println(dict.search("bleiben").equals("bullshit"));
			System.out.println(dict.remove("fahrrad").equals("bike"));
			System.out.println(dict.remove("fahrrad") == null);
		}
		
	}
}
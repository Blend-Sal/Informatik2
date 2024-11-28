package map;

import list.List;
import tuple.Tuple;


public class TreeMapJqwikTest extends ADTMapJqwikTest {

	@Override
	protected <K extends Comparable<K>,V> Map<K,V> empty() {
		return TreeMap.empty();
	}
	
	@Override
  protected <K extends Comparable<K>,V> Map<K,V> fromList(List<Tuple<K,V>> list) {
		return TreeMap.fromList(list);
	}
	
	@Override
  protected Map<String,Integer> wordMap(String s) {
		return TreeMap.wordMap(s);
	}
	
}

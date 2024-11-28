package set;

import list.List;

public class TreeSetJqwikTest extends ADTSortedSetJqwikTest {
	
	@Override
	protected <A extends Comparable<A>> SortedSet<A> empty() {
		return TreeSet.empty();
	}	
	
	@Override
	protected <A extends Comparable<A>> SortedSet<A> fromList(List<A> list){
		return TreeSet.fromList(list);
	}

	@Override
	protected <A extends Comparable<A>> SortedSet<A> set(A... list){
		return TreeSet.set(list);
	}
}

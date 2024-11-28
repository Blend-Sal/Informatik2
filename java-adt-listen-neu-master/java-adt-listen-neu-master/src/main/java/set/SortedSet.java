package set;

import fpinjava.Result;
import list.List;

public interface SortedSet<A extends Comparable<A>> extends Set<A> {


    A findMax();

    A findMin();

    Result<A> lookupMax();

    Result<A> lookupMin();


}



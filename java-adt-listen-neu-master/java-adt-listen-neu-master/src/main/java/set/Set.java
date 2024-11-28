package set;

import fpinjava.Function;
import fpinjava.Result;
import list.List;


public interface Set<A> {

    Set<A> insert(A e);

    Set<A> delete(A e);

    int size();

    boolean isEmpty();

    A findEq(A e);

    List<A> toList();

    boolean isEqualTo(Set<A> other);

    Result<A> lookupEq(A e);

    boolean equals(Object o);

    Set<A> intersection(Set<A> s);

    boolean disjoint(Set<A> s);

    boolean isSubsetOf(Set<A> s);

    boolean any(Function<A, Boolean> p);

    boolean all(Function<A, Boolean> p);

    String toString();

    Set<A> union(Set<A> s);

    boolean member(A x);

    <B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs);

    <B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs);

    Set<A> filter(Function<A, Boolean> f, Set<A> xs);

    <B> Set<B> map(Function<A, B> f, Set<A> xs);


}


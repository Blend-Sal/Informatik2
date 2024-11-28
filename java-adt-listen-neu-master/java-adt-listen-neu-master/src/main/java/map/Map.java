package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.Set;
import tuple.Tuple;

import static tuple.Tuple.tuple;

public interface Map<K extends Comparable<K>, V> {

    Set<Entry<K, V>> entrySet();

    Map<K, V> insert(K key, V value);

    Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value);

    <K1 extends Comparable<K1>, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> set);

    boolean isEmpty();

    boolean member(K key);

    Map<K, V> delete(K key);

    V get(K key);

    int size();

    List<Tuple<K, V>> toList();

    List<K> keys();

    Set<K> keysSet();

    List<V> elems();

    boolean all(Function<K, Function<V, Boolean>> p);

    boolean allKeys(Function<K, Boolean> p);

    boolean isSubmapOf(Map<K, V> m);

    boolean isEqualTo(Map<K, V> m);

    boolean equals(Object o);

    <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s);

    <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s);

    Map<K, V> filter(Function<V, Boolean> p);

    <V2> Map<K, V2> map(Function<V, V2> f);

    Map<K, V> union(Map<K, V> m);

    Map<K, V> intersection(Map<K, V> m);

    Result<V> lookup(K key);


}
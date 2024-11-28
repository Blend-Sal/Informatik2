package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.ListSet;
import set.Set;
import tuple.Tuple;

import static list.List.list;
import static list.List.words;
import static tuple.Tuple.tuple;


public class ListMap<K extends Comparable<K>, V> implements Map<K, V> {
    private final Set<Entry<K, V>> set;


    private ListMap() {
        this(ListSet.empty());
        //set = ListSet.empty();
    }

    private ListMap(Set<Entry<K, V>> s) {
        set = s;
    }

    // Aufgabe B
    public static <K extends Comparable<K>, V> Map<K, V> empty() {
        return new ListMap<K, V>();
    }

    public static <K extends Comparable<K>, V> Map<K, V> fromList(List<Tuple<K, V>> list) {
        return new ListMap<K, V>(list.foldr(x -> y -> y.insert(Entry.entry(x.fst, x.snd)), ListSet.empty()));
    }

    // Aufgabe M
    public static Map<String, Integer> wordMap(String s) {
        return words(s).foldr(x -> y -> y.insertWith(a -> b -> a + b, x, 1), empty());
    }


    // Aufgabe E

    // O(n)
    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    //O(n)
    @Override
    public <K1 extends Comparable<K1>, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> set) {
        return new ListMap<K1, V1>(set);
    }

    //O(n)
    @Override
    public List<Tuple<K, V>> toList() {
        return set.foldr(x -> y -> y.cons(tuple(x.KEY, x.VALUE)), list(), set);
    }

    //O(n)
    @Override
    public List<K> keys() {
        return set.foldr(x -> y -> y.cons(x.KEY), list(), set);
    }

    //O(n)
    @Override
    public Set<K> keysSet() {
        return set.foldr(x -> y -> y.insert(x.KEY), ListSet.empty(), set);
    }

    //O(n)
    @Override
    public List<V> elems() {
        return set.foldr(x -> y -> y.cons(x.VALUE), list(), set);
    }

    //Aufgabe A

    public Map<K, V> insert(K key, V value) {
        return fromEntrySet(set.insert(Entry.entry(key, value)));
    }

    @Override
    public boolean member(K key) {
        return set.member(Entry.entry(key, null));
    }

    @Override
    public Map<K, V> delete(K key) {
        return new ListMap<>(set.delete(Entry.entry(key, null)));
    }

    @Override
    public V get(K key) {
        return !member(key) ? null : set.findEq(new Entry<K, V>(key)).VALUE;
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public int size() {
        return set.size();
    }

    //Aufgabe D
    @Override
    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
        return !member(key) ? insert(key, value) : insert(key, f.apply(value).apply(get(key)));
    }

    //Aufgabe F
    @Override
    public boolean all(Function<K, Function<V, Boolean>> p) {
        return entrySet().all(x -> p.apply(x.KEY).apply(x.VALUE));
    }

    @Override
    public boolean allKeys(Function<K, Boolean> p) {
        return keysSet().all(p);
    }

    //Aufgabe G
    //O(n)
    @Override
    public boolean isSubmapOf(Map<K, V> m) {
        return set.isSubsetOf(m.entrySet());
    }

    @Override
    //O(n)
    public boolean isEqualTo(Map<K, V> o) {
        return keysSet().isEqualTo(o.keysSet()) && elems().toSet().isEqualTo(o.elems().toSet());
    }

    //Aufgabe C
    @Override
    public String toString() {
        return isEmpty() ? "{}" : set.toString();
    }

    // Aufgabe I
    //O(n)
    @Override
    public <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s) {
        return elems().foldr(x -> y -> f.apply(x).apply(y), s);
    }

    //O(n)
    @Override
    public <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s) {
        return elems().foldl(x -> y -> f.apply(x).apply(y), s);
    }

    //O(n)
    @Override
    public Map<K, V> filter(Function<V, Boolean> p) {
        return fromEntrySet(entrySet().filter(x -> p.apply(x.VALUE), set));
    }

    //O(n)
    @Override
    public <V2> Map<K, V2> map(Function<V, V2> f) {
        return fromEntrySet(entrySet().map(x -> Entry.entry(x.KEY, f.apply(x.VALUE)), set));
    }

    //Aufgabe J
    //O(n)
    @Override
    public Map<K, V> union(Map<K, V> m) {
        return fromEntrySet(set.union(m.entrySet()));
    }

    //O(n)
    @Override
    public Map<K, V> intersection(Map<K, V> m) {
        return fromEntrySet(set.intersection(m.entrySet()));

    }

    // Aufgabe A(lookup)
    @Override
    public Result<V> lookup(K key) {
        return !member(key) ? Result.empty() : Result.success(set.findEq(Entry.entry(key, null)).VALUE);
    }

    // Aufgabe H

    public boolean equals(Object o) {
        return o instanceof Map && isEqualTo((Map<K, V>) o);
    }

}

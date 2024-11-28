package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.Set;
import set.SortedSet;
import set.TreeSet;
import tuple.Tuple;

import static list.List.list;
import static list.List.words;
import static tuple.Tuple.tuple;


public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private final SortedSet<Entry<K, V>> set;

    @SuppressWarnings("unchecked")
    private TreeMap() {
        this(TreeSet.empty());
    }

    private TreeMap(SortedSet<Entry<K, V>> s) {
        set = s;
    }

    //Aufgabe A

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public <K1 extends Comparable<K1>, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> set) {
        return new TreeMap<K1, V1>((SortedSet<Entry<K1, V1>>) set);
    }

    public Map<K, V> insert(K key, V value) {
        return fromEntrySet(set.insert(Entry.entry(key, value)));
    }

    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
        return !member(key) ? insert(key, value) : insert(key, f.apply(value).apply(get(key)));
    }

    @Override
    public boolean member(K key) {
        return set.member(Entry.entry(key));
    }

    @Override
    public Map<K, V> delete(K key) {
        return fromEntrySet(set.delete(Entry.entry(key)));
    }

    @Override
    public V get(K key) {
        return !member(key) ? null : set.findEq(new Entry<K, V>(key)).VALUE;
    }

    @Override
    public Result<V> lookup(K key) {
        return !member(key) ? Result.empty() : Result.success(set.findEq(Entry.entry(key)).VALUE);
    }


    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public List<Tuple<K, V>> toList() {
        return set.foldr(x -> y -> y.cons(tuple(x.KEY, x.VALUE)), list(), set);
    }

    @Override
    public boolean all(Function<K, Function<V, Boolean>> p) {
        return entrySet().all(x -> p.apply(x.KEY).apply(x.VALUE));
    }

    @Override
    public boolean allKeys(Function<K, Boolean> p) {
        return keysSet().all(p);
    }

    @Override
    public boolean isSubmapOf(Map<K, V> m) {
        //return set.isSubsetOf(m.entrySet());
        return this.all(ks -> vs -> m.keys().elem(ks) && m.elems().elem(vs));
    }

    public boolean isEqualTo(Map<K, V> o) {
        //return elems().isEqualTo(o.elems()) && keys().isEqualTo(o.keys());
        return this.isSubmapOf(o) && o.isSubmapOf(this);
    }

    @Override
    public List<K> keys() {
        return set.foldr(x -> y -> y.cons(x.KEY), list(), set);
    }

    @Override
    public Set<K> keysSet() {
        return set.foldr(x -> y -> (SortedSet<K>) y.insert(x.KEY), TreeSet.empty(), set);
    }

    @Override
    public List<V> elems() {
        return set.foldr(x -> y -> y.cons(x.VALUE), list(), set);
    }

    @Override
    public <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s) {
        return elems().foldr(x -> y -> f.apply(x).apply(y), s);
    }

    @Override
    public <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s) {
        return elems().foldl(x -> y -> f.apply(x).apply(y), s);
    }

    @Override
    public Map<K, V> filter(Function<V, Boolean> p) {
        return fromEntrySet(entrySet().filter(x -> p.apply(x.VALUE), set));
    }

    @Override
    public <V2> Map<K, V2> map(Function<V, V2> f) {
        return fromEntrySet(entrySet().map(x -> Entry.entry(x.KEY, f.apply(x.VALUE)), set));
    }

    @Override
    public Map<K, V> union(Map<K, V> m) {
        return fromEntrySet(set.union(m.entrySet()));
    }

    @Override
    public Map<K, V> intersection(Map<K, V> m) {
        return fromEntrySet(set.intersection(m.entrySet()));
    }

    //Aufgabe B

    public static <K extends Comparable<K>, V> Map<K, V> empty() {
        return new TreeMap<>();
    }

    public static <K extends Comparable<K>, V> Map<K, V> fromList(List<Tuple<K, V>> list) {
        return new TreeMap<K, V>(list.foldr(x -> y -> TreeSet.fromSet(y.insert(Entry.entry(x.fst, x.snd))), TreeSet.empty()));
    }

    public static Map<String, Integer> wordMap(String s) {
        return words(s).foldr(x -> y -> y.insertWith(a -> b -> a + b, x, 1), empty());
    }

    //Aufgabe C

    @Override
    public String toString() {
        return isEmpty() ? "{}" : set.toString();
    }

    //Aufgabe D

    public boolean equals(Object o) {
        return o instanceof Map && isEqualTo((Map<K, V>) o);
    }

}

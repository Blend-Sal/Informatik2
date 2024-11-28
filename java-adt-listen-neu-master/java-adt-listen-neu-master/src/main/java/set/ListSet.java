package set;

import fpinjava.Function;
import fpinjava.Result;
import fpinjava.TailCall;
import list.List;

import static fpinjava.TailCall.ret;
import static fpinjava.TailCall.sus;
import static list.List.*;


//Aufgabe L: Wenn null das Ergebnis ist wird dementsprechend keine Null Pointer Exception, dadurch werden die bekannten Konsequenzen nicht auftreten
//Aufgabe M: Der Datentyp "Result" speicher das Ergebnis in einem Container (Result.empty(), Result.failure(), Result.success(), dadruch wird im Falle
//einer Exception keine geworfen, da dieser im Container Result.failure() gespeichert wird um die uns bereits bekannten Probleme von Exceptions zu umgehen
//beziehungsweise zu vermeiden.

public class ListSet<A> implements Set<A> {
    final private List<A> set;

    private ListSet(List<A> list) {
        this.set = list;
    }

    private ListSet() {
        this.set = list();
    }

    //Aufgabe B
    public static <A> Set<A> empty() {
        return new ListSet<>();
    }

    public static <A> Set<A> fromList(List<A> list) {
        return list.foldl(s -> s::insert, empty());
    }

    public static <A> Set<A> fromSet(Set<A> s) {
        return new ListSet<>(s.toList());
    }

    @SafeVarargs
    public static <A> Set<A> set(A... es) {
        return new ListSet<>(list(es));
    }

    //Aufgabe A
    // O(n)
    public Set<A> insert(A e) {
        return new ListSet<>(set.delete(e).cons(e));

    }

    // O(n)
    public Set<A> delete(A e) {
        return new ListSet<>(set.delete(e));
    }


    // O(n)
    public boolean member(A e) {
        return set.elem(e);
    }


    // O(n)
    public int size() {
        return set.length();
    }

    // O(1)
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // O(n)
    public A findEq(A e) {
        return (set.isEmpty()) ? null : set.finde(x -> x.equals(e));
    }

    // O(1)
    public List<A> toList() {
        return set;
    }

    //Aufgabe C
    // O(n)
    @Override
    public String toString() {
        return String.format("{%sEMPTY}",
                toString(new StringBuilder(), set).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
        return list.isEmpty()
                ? ret(acc)
                : sus(() -> toString(acc.append(list.head()).append(", "),
                list.tail()));
    }


    //Aufgabe D
    // O(n)
    public boolean any(Function<A, Boolean> p) {
        return set.any(p);
    }

    // O(n)
    public boolean all(Function<A, Boolean> p) {
        return set.all(p);
    }

    //Aufgabe D Teil 2
    // O(n) //O(n^2)
    public boolean isSubsetOf(Set<A> s) {
        return set.all(s::member);
    }

    // O(n)
    @Override
    public boolean isEqualTo(Set<A> other) {
        return isSubsetOf(other) && other.isSubsetOf(this);
    }

    // O(n)
    public boolean disjoint(Set<A> s) {
        return !set.any(s::member);
    }

    //Aufgabe F

    @Override
    public <B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs) {
        return List.foldr(x -> y -> f.apply(x).apply(y), s, xs.toList());
    }

    @Override
    public <B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs) {
        return List.foldl(x -> y -> f.apply(x).apply(y), s, xs.toList());
    }

    @Override
    public Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return new ListSet<>(xs.toList().filter(f));
    }

    public <B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return new ListSet<>(xs.toList().map(f));
    }


    //Aufgabe G
    public Set<A> union(Set<A> s) {
        return foldr(x -> y -> y.insert(x), s, this);
    }

    public Set<A> intersection(Set<A> s) {
        return fromList(set.filter(s::member));
    }

    //Aufgabe H

    public boolean equals(Object o) {
        return o instanceof ListSet && isEqualTo((ListSet) o);
    }

    //Aufgabe I


    public static Set<String> wordSet(String s) {
        return fromList(words(s));
    }

    /*
    public static void main(String[] args) {
        System.out.println(wordSet("Elfen helfen Elfen"));
        System.out.println(wordSet("Elfen helfen Elfen").isEqualTo(set("Elfen","helfen")));
    }
    */


    //Aufgabe M

    // O(n)
    public Result<A> lookupEq(A e) {

        return set.find(x -> x.equals(e));
    }


}

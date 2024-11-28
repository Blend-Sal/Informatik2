package set;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import tree.bst.Tree;
import list.List.*;

import static list.List.words;
import static tree.bst.Tree.tree;


public class TreeSet<A extends Comparable<A>> implements SortedSet<A> {
    private final Tree<A> tree;

    private TreeSet() {
        this.tree = tree();
    }

    public TreeSet(Tree<A> tree) {
        this.tree = tree;
    }

    public static <A extends Comparable<A>> SortedSet<A> empty() {
        return new TreeSet<A>();
    }

    public static <A extends Comparable<A>> SortedSet<A> fromList(List<A> list) {
        return list.foldl(s -> x -> (SortedSet<A>) s.insert(x), empty());
    }

    @SafeVarargs
    public static <A extends Comparable<A>> SortedSet<A> set(A... as) {
        return new TreeSet<A>(tree(as));
    }

    public static <A extends Comparable<A>> SortedSet<A> fromSet(Set<A> s) {
        return new TreeSet<A>(tree(s.toList()));
    }

    public static SortedSet<String> wordSet(String s) {
        return fromList(words(s));
    }


    //O(n)
    @Override
    public Set<A> insert(A e) {
        return new TreeSet<A>(tree.insert(e));
    }

    //O(n)
    @Override
    public Set<A> delete(A e) {
        return new TreeSet<A>(tree.delete(e));
    }

    //O(n)
    @Override
    public boolean member(A e) {
        return tree.member(e);
    }

    //O(n)
    @Override
    public <B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs) {
        return tree.toList().toSet().foldr(f, s, xs);
    }

    //O(n)
    @Override
    public <B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs) {
        return tree.toList().toSet().foldl(f, s, xs);
    }

    //O(n)
    @Override
    public Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return fromList(xs.toList().filter(f));
    }
    //O(n)

    public <B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return this.toList().map(f).toSet();
    }

    //O(n)
    @Override
    public int size() {
        return tree.size();
    }

    //O(1)
    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    //O(n)
    @Override
    public boolean any(Function<A, Boolean> p) {
        return toList().any(p);
    }

    //O(n)
    @Override
    public boolean all(Function<A, Boolean> p) {
        return toList().all(p);
    }

    //O(n^2)
    @Override
    public boolean isSubsetOf(Set<A> s) {
        return all(s::member);
    }

    //O(n^2)
    @Override
    public Set<A> union(Set<A> s) {
        return fromSet(tree.inorder().toSet().union(s));
    }

    //O(n^2)
    @Override
    public Set<A> intersection(Set<A> s) {
        return fromSet(tree.inorder().toSet().intersection(s));
    }

    //O(n)
    @Override
    public boolean isEqualTo(Set<A> s) {
        return isSubsetOf(s) && s.isSubsetOf(this);
    }

    //O(n^2)
    @Override
    public boolean disjoint(Set<A> s) {
        return !any(s::member);
        //return toList().toSet().disjoint(s);
    }

    //O(n)
    @Override
    public A findEq(A e) {
        return tree.findEq(e);
    }

    //O(1)
    @Override
    public List<A> toList() {
        return tree.inorder();
    }

    //O(n)
    @Override
    public Result<A> lookupEq(A e) {
        return tree.lookupEq(e);
    }

    //O(n)
    @Override
    public A findMax() {
        return tree.findMax();
    }

    //O(n)
    @Override
    public A findMin() {
        return tree.findMin();
    }

    @Override
    public Result<A> lookupMax() {
        return tree.lookupMax();
    }

    @Override
    public Result<A> lookupMin() {
        return tree.lookupMin();
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SortedSet && isEqualTo((Set<A>) o);
    }

}

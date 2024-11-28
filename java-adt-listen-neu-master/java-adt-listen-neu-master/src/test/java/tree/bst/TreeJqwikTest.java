package tree.bst;

import fpinjava.Result;
import list.List;
import net.jqwik.api.*;
import net.jqwik.api.Tuple.*;

import java.io.Serializable;
import java.util.Objects;

import static list.List.list;
import static tree.bst.Tree.tree;

public class TreeJqwikTest {

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple3<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable, Integer>> insertdata() {
        return Table.of(
                Tuple.of(Tree.empty(), 3, 1),
                Tuple.of(tree(1, 2, 3), 4, 4),
                Tuple.of(tree("L", "M", "N", "O", "P"), "Q", 6));
    }

    @FromData("insertdata")
    @Property
    public <A extends Comparable<A>> boolean testinsert(@ForAll Tree<A> t, @ForAll A e, @ForAll int result) {

        return t.insert(e).size() == result;
    }

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable>> memberdata() {
        return Table.of(
                Tuple.of(Tree.empty(), 4),
                Tuple.of(tree(5, 4, 3, 2, 1), 6),
                Tuple.of(tree("A", "B", "C"), "D")
        );

    }

    @FromData("memberdata")
    @Property
    public <A extends Comparable<A>> boolean test_member(@ForAll Tree<A> t, @ForAll A e) {
        return t.insert(e).member(e);
    }

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable>> sizedata() {
        return Table.of(
                Tuple.of(Tree.empty(), 0),
                Tuple.of(tree(2, 3, 4, 5, 6), 5),
                Tuple.of(tree("A", "B", "C", "D"), 4)
        );
    }


    @FromData("sizedata")
    @Property
    public <A extends Comparable<A>> boolean test_size(@ForAll Tree<A> t, @ForAll int result) {

        return t.size() == result;

    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> heightdata() {
        return Table.of(Tuple.of(Tree.empty(), -1),
                Tuple.of(tree(1, 2, 3), 2),
                Tuple.of(tree(3, 2, 1), 2),
                Tuple.of(tree("A", "B", "C"), 2));
    }

    @FromData("heightdata")
    @Property
    public <A extends Comparable<A>> boolean test_height(@ForAll Tree<A> t, @ForAll int result) {
        return t.height() == result;
    }

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple3<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable, Integer>> deletedata() {
        return Table.of(
                Tuple.of(Tree.empty(), 1, 0),
                Tuple.of(tree(1, 2, 3, 4, 5, 6, 7, 8), 8, 7),
                Tuple.of(tree("L", "M", "N", "O", "P"), "O", 4));
    }

    @FromData("deletedata")
    @Property
    public <A extends Comparable<A>> boolean test_delete(@ForAll Tree<A> t, @ForAll A e, @ForAll int result) {
        return t.delete(e).size() == result;
    }

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Boolean>> isEmptydata() {
        return Table.of(
                Tuple.of(Tree.empty(), true),
                Tuple.of(tree(2, 3, 4, 5, 6), false));
    }

    @FromData("isEmptydata")
    @Property
    public <A extends Comparable<A>> boolean test_isEmpty(@ForAll Tree<A> t, @ForAll boolean result) {
        return t.isEmpty() == result;
    }

    @Data
    <A extends Comparable<A>>
    Iterable<Tuple3<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable, Serializable>> findEqdata() {
        return Table.of(
                Tuple.of(tree(5, 6, 7), 5, 5),
                Tuple.of(tree("E", "F", "G"), "G", "G"));
    }


    @FromData("findEqdata")
    @Property
    public <A extends Comparable<A>> boolean test_findEq(@ForAll Tree<A> t, @ForAll A e, @ForAll A result) {
        return t.findEq(e).equals(result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple3<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable, Result<?>>> lookupEqdata() {
        return Table.of(Tuple.of(Tree.empty(), 1, Result.empty()),
                Tuple.of(tree(1, 2, 3, 4, 5), 3, Result.success(3)),
                Tuple.of(tree("A", "B", "C", "D"), "B", Result.success("B")),
                Tuple.of(tree("a", "b", "c", "d"), "s", Result.empty()));
    }

    @FromData("lookupEqdata")
    @Property
    public <A extends Comparable<A>> boolean test_lookupEq(@ForAll Tree<A> t, @ForAll A e, @ForAll Result<A> result) {
        return Objects.equals(t.lookupEq(e), result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable>> findMindata() {
        return Table.of(Tuple.of(tree(1, 10, 20, 30, 40, 50), 1),
                Tuple.of(tree("B", "S", "P", "N"), "B"));
    }

    @FromData("findMindata")
    @Property
    public <A extends Comparable<A>> boolean test_findMin(@ForAll Tree<A> t, @ForAll A result) {
        return Objects.equals(t.findMin(), result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Serializable>> findMaxdata() {
        return Table.of(Tuple.of(tree(11, 52, 73, 44, 335, 340), 340),
                Tuple.of(tree("B", "S", "P", "N"), "S"));
    }

    @FromData("findMaxdata")
    @Property
    public <A extends Comparable<A>> boolean test_findMax(@ForAll Tree<A> t, @ForAll A result) {
        return Objects.equals(t.findMax(), result);
    }


    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> sizeLeafdata() {
        return Table.of(Tuple.of(Tree.empty(), 0),
                Tuple.of(tree(11, 52, 73, 44, 335, 340), 2),
                Tuple.of(tree("B", "S", "P", "N"), 1));
    }

    @FromData("sizeLeafdata")
    @Property
    public <A extends Comparable<A>> boolean test_sizeLeaf(@ForAll Tree<A> t, @ForAll int result) {
        return Objects.equals(t.sizeLeaf(), result);
    }


    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> sizeInnerdata() {
        return Table.of(Tuple.of(Tree.empty(), 0),
                Tuple.of(tree(11, 52, 73, 44, 335, 340), 4),
                Tuple.of(tree("B", "S", "P", "N"), 3));
    }

    @FromData("sizeInnerdata")
    @Property
    public <A extends Comparable<A>> boolean test_sizeInner(@ForAll Tree<A> t, @ForAll int result) {
        return Objects.equals(t.sizeInner(), result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> sizeHalfdata() {
        return Table.of(Tuple.of(Tree.empty(), 0),
                Tuple.of(tree(11, 52, 73, 44, 335, 340), 3),
                Tuple.of(tree("A", "B", "C", "D"), 3));
    }

    @FromData("sizeHalfdata")
    @Property
    public <A extends Comparable<A>> boolean dataTest_sizeHalf(@ForAll Tree<A> t, @ForAll int result) {
        return Objects.equals(t.sizeHalf(), result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> sizeFulldata() {
        return Table.of(Tuple.of(Tree.empty(), 0),
                Tuple.of(tree(11, 52, 73, 44, 335, 340), 1),
                Tuple.of(tree("A", "B", "C", "D"), 0));
    }

    @FromData("sizeFulldata")
    @Property
    public <A extends Comparable<A>> boolean test_sizeFull(@ForAll Tree<A> t, @ForAll int result) {
        return Objects.equals(t.sizeFull(), result);
    }


    @Data
    <A extends Comparable<A>>
    Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Integer>> sizeEmptydata() {
        return Table.of(
                Tuple.of(Tree.empty(), 1),
                Tuple.of(tree(2, 3, 4, 5, 6), 6),
                Tuple.of(tree("A", "B", "C", "D", "E", "F", "G", "H"), 9));

    }


    @FromData("sizeEmptydata")
    @Property
    public <A extends Comparable<A>> boolean test_sizeEmpty(@ForAll Tree<A> t, @ForAll int result) {

        return t.sizeEmpty() == result;

    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Result<?>>> lookupMindata() {
        return Table.of(Tuple.of(Tree.empty(), Result.empty()),
                Tuple.of(tree(12, 3, 4, 5, 6), Result.success(3)),
                Tuple.of(tree("B", "S", "P", "N"), Result.success("B")));
    }

    @FromData("lookupMindata")
    @Property
    public <A extends Comparable<A>> boolean test_lookupMin(@ForAll Tree<A> t, @ForAll Result<A> result) {
        return Objects.equals(t.lookupMin(), result);
    }

    @Data
    <A extends Comparable<A>> Iterable<Tuple2<Tree<? extends Comparable<? extends Comparable<?>>>, Result<?>>> lookupMaxdata() {
        return Table.of(Tuple.of(Tree.empty(), Result.empty()),
                Tuple.of(tree(2, 3, 4, 5, 6), Result.success(6)),
                Tuple.of(tree("B", "S", "P", "N"), Result.success("S")));
    }

    @FromData("lookupMaxdata")
    @Property
    public <A extends Comparable<A>> boolean test_lookupMax(@ForAll Tree<A> t, @ForAll Result<A> result) {
        return Objects.equals(t.lookupMax(), result);
    }


}




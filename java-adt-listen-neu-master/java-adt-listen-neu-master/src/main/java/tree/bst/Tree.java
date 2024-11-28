package tree.bst;

import fpinjava.Function;
import fpinjava.Result;
import list.List;

import static list.List.*;

public abstract class Tree<A extends Comparable<A>> {

    @SuppressWarnings("rawtypes")
    public final static Tree EMPTY = new Empty();

    public abstract A value();

    public abstract Tree<A> left();

    public abstract Tree<A> right();

    public abstract Tree<A> insert(A value);

    public abstract boolean member(A value);

    public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
        return list.foldl(t -> t::insert, empty());
    }

    @SafeVarargs
    public static <A extends Comparable<A>> Tree<A> tree(A... as) {
        return tree(list(as));
    }

    public abstract int size();

    public abstract int height();

    protected abstract Tree<A> removeMerge(Tree<A> ta);

    public abstract Tree<A> delete(A a);

    public abstract boolean isEmpty();

    public abstract A findMin();

    public abstract A findMax();

    public abstract Result<A> lookupEq(A x);

    public abstract int sizeEmpty();

    public abstract int sizeLeaf();

    public abstract int sizeInner();

    public abstract int sizeFull();

    public abstract int sizeHalf();


    public abstract List<A> postorder();

    public abstract A findEq(A x);

    public abstract int sizeFilter(Function<Tree<A>, Boolean> p);

    public abstract boolean isLeaf();

    public abstract boolean isHalf();

    public abstract boolean isFull();

    public abstract boolean isInner();

    public abstract Result<A> lookupMax();

    public abstract Result<A> lookupMin();

    public abstract List<A> levelorder();

    public abstract List<A> preorder();

    public abstract List<A> inorder();

    public abstract <B> B fold(Function<B, Function<A, Function<B, B>>> f, B identity);


    @SuppressWarnings("unchecked")
    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY;
    }


    private static class Empty<A extends Comparable<A>> extends Tree<A> {
        @Override
        public A value() {
            throw new IllegalStateException("value() called on empty");
        }

        @Override
        public Tree<A> left() {
            throw new IllegalStateException("left() called on empty");
        }

        @Override
        public Tree<A> right() {
            throw new IllegalStateException("right() called on empty");
        }

        @Override
        public Tree<A> insert(A value) {
            return new T<>(empty(), value, empty());
        }

        @Override
        public boolean member(A value) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public int height() {
            return -1;
        }

        protected Tree<A> removeMerge(Tree<A> ta) {
            return ta;
        }

        @Override
        public Tree<A> delete(A a) {
            return empty();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public A findMin() {
            throw new IllegalStateException("findMin() called on empty");
        }

        @Override
        public A findMax() {
            throw new IllegalStateException("findMax() called on empty");
        }


        @Override
        public String toString() {
            return "E";
        }


        @Override
        public Result<A> lookupEq(A x) {
            return Result.empty();
        }

        @Override
        public A findEq(A x) {
            return null;
        }

        @Override
        public int sizeEmpty() {
            return 1;
        }

        @Override
        public int sizeLeaf() {
            return 0;
        }

        @Override
        public int sizeInner() {
            return 0;
        }

        @Override
        public int sizeFull() {
            return 0;
        }

        @Override
        public int sizeHalf() {
            return 0;
        }


        @Override
        public List<A> postorder() {
            return list();
        }

        @Override
        public int sizeFilter(Function<Tree<A>, Boolean> p) {
            return (p.apply(this) ? 1 : 0);
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public boolean isHalf() {
            return false;
        }

        @Override
        public boolean isFull() {
            return false;
        }

        @Override
        public boolean isInner() {
            return false;
        }

        @Override
        public Result<A> lookupMax() {
            return Result.empty();
        }

        @Override
        public Result<A> lookupMin() {
            return Result.empty();
        }


        @Override
        public List<A> levelorder() {
            return null;
        }

        @Override
        public List<A> preorder() {
            return null;
        }

        @Override
        public List<A> inorder() {
            return list();
        }

        @Override
        public <B> B fold(Function<B, Function<A, Function<B, B>>> f, B identity) {
            return identity;
        }


    }

    private static class T<A extends Comparable<A>> extends Tree<A> {

        private final Tree<A> left;
        private final Tree<A> right;
        private final A value;

        private T(Tree<A> left, A value, Tree<A> right) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        @Override
        public A value() {
            return value;
        }

        @Override
        public Tree<A> left() {
            return left;
        }

        @Override
        public Tree<A> right() {
            return right;
        }


        @Override
        public Tree<A> insert(A value) {
            return value.compareTo(this.value) < 0
                    ? new T<>(left.insert(value), this.value, right)
                    : value.compareTo(this.value) > 0
                    ? new T<>(left, this.value, right.insert(value))
                    : new T<>(this.left, value, this.right);
        }

        public boolean member(A value) {
            return value.compareTo(this.value) < 0
                    ? left.member(value)
                    : value.compareTo(this.value) <= 0 || right.member(value);
        }

        public int size() {
            return 1 + left.size() + right.size();
        }

        public int height() {
            return 1 + Math.max(left.height(), right.height());
        }

        protected Tree<A> removeMerge(Tree<A> ta) {
            if (ta.isEmpty()) {
                return this;
            }
            if (ta.value().compareTo(value) < 0) {
                return new T<>(left.removeMerge(ta), value, right);
            } else if (ta.value().compareTo(value) > 0) {
                return new T<>(left, value, right.removeMerge(ta));
            }
            throw new IllegalStateException("We shouldn't be here");
        }

        public Tree<A> delete(A a) {
            if (a.compareTo(this.value) < 0) {
                return new T<>(left.delete(a), value, right);
            } else if (a.compareTo(this.value) > 0) {
                return new T<>(left, value, right.delete(a));
            } else {
                return left.removeMerge(right);
            }
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        //O(n)
        @Override
        public A findMin() {
            return left.isEmpty() ? value : left.findMin();
        }

        //O(n)
        @Override
        public A findMax() {
            return right.isEmpty() ? value : right.findMax();
        }


        @Override
        public A findEq(A e) {
            return e.compareTo(value) < 0
                    ? left.findEq(e)
                    : e.compareTo(value) > 0
                    ? right.findEq(e)
                    : value;
        }

        @Override
        public String toString() {
            return String.format("(T %s %s %s)", left, value, right);
        }

        @Override
        public Result<A> lookupEq(A e) {
            return e.compareTo(value) < 0
                    ? left.lookupEq(e)
                    : e.compareTo(value) > 0
                    ? right.lookupEq(e)
                    : Result.success(value);
        }

        //O(n)
        @Override
        public int sizeEmpty() {
            return sizeFilter(Tree::isEmpty);
        }

        //O(n)
        public int sizeLeaf() {
            return sizeFilter(Tree::isLeaf);
        }

        //O(n)
        @Override
        public int sizeInner() {
            return sizeFilter(Tree::isInner);
        }

        //O(n)
        @Override
        public int sizeFull() {
            return sizeFilter(Tree::isFull);
        }

        //O(n)
        @Override
        public int sizeHalf() {
            return sizeFilter(Tree::isHalf);
        }


        @Override
        public <B> B fold(Function<B, Function<A, Function<B, B>>> f, B identity) {
            return f.apply(left.fold(f, identity)).apply(value).apply(right.fold(f, identity));
        }

        //O(n^2)
        @Override
        public List<A> postorder() {
            return null;
        }

        public int sizeFilter(Function<Tree<A>, Boolean> p) {
            return (p.apply(this) ? 1 : 0) + left().sizeFilter(p) + right().sizeFilter(p);
        }

        @Override
        public boolean isLeaf() {
            return left().isEmpty() && right().isEmpty();
        }


        @Override
        public boolean isHalf() {
            return left().isEmpty() ^ right().isEmpty();
        }

        @Override
        public boolean isFull() {
            return !left().isEmpty() && !right().isEmpty();
        }

        @Override
        public boolean isInner() {
            return isHalf() || isFull();
        }

        @Override
        public Result<A> lookupMax() {
            return right.lookupMax().orElse(() -> Result.success(value));
        }

        @Override
        public Result<A> lookupMin() {
            return left.lookupMin().orElse(() -> Result.success(value));
        }

        @Override
        public List<A> levelorder() {
            return null;
        }

        @Override
        public List<A> inorder() {
            return fold(l -> v -> r -> append(l, r.cons(v)), list());
        }

        @Override
        public List<A> preorder() {
            return null;
        }


/*
        public static void main(String[] args) {
            Tree<Integer> tree1 = tree(1, 2, 3, 4, 5);
            Tree<Integer> tree2 = tree(6, 7, 8, 9, 10);
            System.out.println(tree1.sizeLeaf());
            System.out.println(tree2.sizeInner());
            System.out.println(tree2.sizeHalf());
            System.out.println(tree1.sizeFull());
            System.out.println(tree1.postorder(tree1));
        }

 */

    }

    public List<A> toList() {
        return inorder();
    }


}

package list;


import fpinjava.Function;
import fpinjava.Result;
import fpinjava.TailCall;
import map.ListMap;
import map.Map;
import set.Set;

import static fpinjava.TailCall.ret;
import static fpinjava.TailCall.sus;
import static set.ListSet.fromList;

/* Aufgabe B: Der entscheidende Vorteil bei dem StringBuilder ist das es eine append Methode besitzt.
D 4
Ein entscheidender Vorteil bei der Linksfaltung ist das die Linksfaltung mit einem konstanten Speicherbedarf arbeitet gegenüber der Rechtsfaltung
da die bei jedem Rechenschritt direkt berechnet wird wie hoch der Speicherbedarf ist unabhängig zu der verarbeiteten Liste.*/

/*Aufgabe J:
Das Problem mit der Implementierung von Exceptions ist dass das Programm zum Abstürzen geführt wird auch wenn sonst die Möglichkeit bestehen würde mit
dem Auslösen der Exception weiterzulaufen

Der Vorteil von Option gegenüber von Optional ist das Option es uns erlaubt funktionen zu komponieren obwohl die Daten nicht vorhanden sein können.
*/

/*Aufgabe K
Das Problem was Result löst ist das Umgegehen mit Exceptions.
Die werden als Failure gelagert und führen nicht zum abstürzen des Programmes.
-------------------------------------------------------------------------------------
headOption hat den Vorteil das der Rückgabetyp Result ist und dadurch keine Exceptions hervorgerufen werden die dazu führen dass das Programm abstürzt.

 */


public abstract class List<A> {

    public abstract A head();

    public abstract int length();

    public abstract boolean elem(A x);

    public abstract boolean any(Function<A, Boolean> p);

    public abstract boolean all(Function<A, Boolean> p);

    public abstract <B> List<B> map(Function<A, B> f);

    public abstract List<A> filter(Function<A, Boolean> f);

    public abstract List<A> init();

    public abstract A last();

    public abstract List<A> take(int n);

    public abstract List<A> drop(int n);

    public abstract List<A> takeWhile(Function<A, Boolean> p);

    public abstract List<A> dropWhile(Function<A, Boolean> p);

    public abstract List<A> delete(A x);

    public abstract List<A> reverse();

    public abstract A finde(Function<A, Boolean> f);

    public abstract List<A> tail();

    public abstract boolean isEmpty();

    public abstract List<A> setHead(A h);

    public abstract boolean isEqualTo(List<A> xs);

    public abstract <B> B foldr(Function<A, Function<B, B>> f, B s);

    public abstract <B> B foldl(Function<B, Function<A, B>> f, B s);

    public abstract Result<A> headOption();

    public abstract Result<A> find(Function<A, Boolean> f);

    public abstract boolean equals(Object o);


    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    public static <A> List<A> setHead(List<A> list, A h) {
        if (list.isEmpty()) {
            throw new IllegalStateException("setHead called on an empty list");
        } else {
            return new Cons<>(h, list.tail());
        }
    }

    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    private List() {
    }


    private static class Nil<A> extends List<A> {


        public A finde(Function<A, Boolean> f) {
            return null;
        }

        public String toString() {
            return "[NIL]";
        }

        private Nil() {
        }

        public A head() {
            throw new IllegalStateException("head called en empty list");
        }


        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A h) {
            throw new RuntimeException("To be implemented");
        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            return xs.isEmpty();
        }

        public int length() {
            return 0;
        }

        public List<A> reverse() {
            return list();
        }

        public boolean elem(A x) {
            return false;
        }

        public boolean any(Function<A, Boolean> p) {
            return false;
        }

        public boolean all(Function<A, Boolean> p) {
            return true;
        }

        public <B> List<B> map(Function<A, B> f) {
            return list();
        }

        public List<A> filter(Function<A, Boolean> f) {
            return list();
        }

        public List<A> init() {
            return null;
        }

        public A last() {
            return null;
        }

        public List<A> drop(int n) {
            return this;
        }

        public List<A> take(int n) {
            return list();
        }

        public List<A> takeWhile(Function<A, Boolean> p) {

            return list();
        }

        public List<A> dropWhile(Function<A, Boolean> p) {
            return this;
        }

        public List<A> delete(A x) {
            return list();
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) {

            return s;
        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) {

            return s;
        }

        public static Integer sum(List<Integer> xs) {
            return 0;
        }

        public Result<A> headOption() {
            return Result.empty();
        }

        public Result<A> find(Function<A, Boolean> f) {
            return Result.empty();
        }

        public boolean equals(Object o) {
            return o instanceof Nil;
        }

    }

    private static class Cons<A> extends List<A> {

        public String toString() {
            return String.format("[%sNIL]",
                    toString(new StringBuilder(), this).eval());
        }

        private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
            return list.isEmpty()
                    ? ret(acc)
                    : sus(() -> toString(acc.append(list.head()).append(", "),
                    list.tail()));
        }

        private final A head;


        private final List<A> tail;

        private Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public List<A> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<A> setHead(A h) {
            return new Cons<>(h, tail());
        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            return !xs.isEmpty() && head().equals(xs.head()) && tail().equals(xs.tail());

        }

        public int length() {
            return 1 + tail().length();
        }

        public boolean elem(A x) {
            return this.head().equals(x) || this.tail().elem(x);
        }

        public boolean any(Function<A, Boolean> p) {
            return p.apply(this.head()) || this.tail().any(p);
        }

        public boolean all(Function<A, Boolean> p) {
            return p.apply(this.head()) && this.tail().all(p);
        }

        public <B> List<B> map(Function<A, B> f) {
            return new Cons<>(f.apply(this.head()), this.tail().map(f));
        }

        public List<A> filter(Function<A, Boolean> f) {
            return f.apply(head()) ? new Cons<>(head(), tail().filter(f)) : tail().filter(f);
        }


        public A finde(Function<A, Boolean> f) {
            return f.apply(this.head()) ? this.head() : this.tail().finde(f);
        }

        public List<A> init() {
            return reverse().tail().reverse();
        }

        public A last() {
            return this.reverse().head();
        }

        public List<A> take(int n) {
            return n <= 0 ? list() : new Cons<>(head(), tail().take(n - 1));
        }


        public List<A> drop(int n) {
            return n <= 0 ? this : tail().drop(n - 1);
        }


        public List<A> takeWhile(Function<A, Boolean> p) {
            return p.apply(head()) ? new Cons<>(head(), tail().takeWhile(p)) : list();
        }


        public List<A> dropWhile(Function<A, Boolean> p) {
            return p.apply(head()) ? tail().dropWhile(p) : this;
        }


        public List<A> delete(A x) {
            return !this.head().equals(x) ? append(list(this.head()), this.tail().delete(x)) : this.tail().delete(x);
        }

        public List<A> reverse() {
            return append((this.tail()), list(this.head()));
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return this.isEmpty() ? s : f.apply(this.head()).apply(foldr(f, s, this.tail()));
        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return isEmpty() ? s : foldl(f, f.apply(s).apply(head()), tail());
        }

        public Result<A> headOption() {
            return Result.success(head());
        }

        public Result<A> find(Function<A, Boolean> f) {
            return filter(f).headOption();
        }

        public boolean equals(Object o) {
            return o instanceof Cons && isEqualTo((List<A>) o);
        }

    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Cons<>(a[i], n);
        }
        return n;
    }

    public static Integer sum(List<Integer> xs) {
        return xs.isEmpty() ? 0 : xs.head() + sum(xs.tail());
    }

    public static double prod(List<Integer> xs) {
        return xs.isEmpty() ? 0 : xs.head() * prod(xs.tail());
    }

    public static <A> List<A> append(List<A> list1, List<A> list2) {
        return
                list1.isEmpty() ? list2
                        :
                        new Cons<>(list1.head(), append(list1.tail(), list2));

    }

    public static <A> List<A> concat(List<List<A>> list) {

        return list.isEmpty() ? list() : append(list.head(), concat(list.tail()));
    }

    public static boolean and(List<Boolean> list) {

        return list.isEmpty() || list.head() && and(list.tail());
    }

    public static boolean or(List<Boolean> list) {

        return !list.isEmpty() && (list.head() || or(list.tail()));
    }

    public static Integer minimum(List<Integer> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("Minimum of Empty List");
        } else {
            return (list.length() == 1) ? list.head() : Math.min(list.head(), minimum(list.tail()));

        }
    }

    public static <A extends Comparable<A>> A minimumcomp(List<A> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("minimum of empty List");
        } else {
            return (list.length() == 1) || list.filter(x -> x.compareTo(list.head()) <= 0).length() <= 1 ? list.head() : minimumcomp(list.tail());
        }

    }

    public static Integer maximum(List<Integer> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("Maximum of Empty List");
        } else {
            return (list.length() == 1) ? list.head() : Math.max(list.head(), maximum(list.tail()));
        }
    }

    public static <A extends Comparable<A>> A maximumcomp(List<A> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("maximum of empty List");
        } else {
            return (list.length() == 1) || list.filter(x -> x.compareTo(list.head()) >= 0).length() <= 1 ? list.head() : maximumcomp(list.tail());
        }
    }


    public static <A, B> B foldr(Function<A, Function<B, B>> f, B s, List<A> xs) {
        return xs.isEmpty() ? s : f.apply(xs.head()).apply(foldr(f, s, xs.tail()));
    }

    public static <A, B> B foldr(List<A> list, B n, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? n : f.apply(list.head()).apply(foldr(list.tail(), n, f));
    }

    public <B> B foldr(B acc, List<A> ts, B identity, Function<A, Function<B, B>> f) {
        return ts.isEmpty() ? acc : foldr(f.apply(ts.head()).apply(acc), ts.tail(), identity, f);
    }

    public <B> B foldr(B identity, Function<A, Function<B, B>> f) {
        return foldr(identity, this.reverse(), identity, f);
    }

    public static <A, B> B foldl(Function<B, Function<A, B>> f, B s, List<A> xs) {
        return xs.isEmpty() ? s : foldl(f, f.apply(s).apply(xs.head()), xs.tail());
    }


    public static Integer sumR(List<Integer> list) {
        return foldr(x -> y -> x + y, 0, list);
    }

    public static Double prodR(List<Double> list) {
        return foldr(x -> y -> x * y, 1.0, list);
    }

    static <A> int lengthR(List<A> list) {
        return foldr(x -> n -> n + 1, 0, list);
    }

    public boolean elemR(A x) {
        return foldr(y -> f -> (this.head().equals(x)), false);
    }

    public boolean anyR(Function<A, Boolean> p) {
        return foldr(x -> y -> p.apply(x) || y, false);
    }

    public boolean allR(Function<A, Boolean> p) {
        return foldr(x -> y -> p.apply(x) && y, true);
    }

    public static boolean andR(List<Boolean> list) {
        return foldr(list, true, x -> y -> x && y);
    }

    public static boolean orR(List<Boolean> list) {
        return foldr(list, false, x -> y -> x || y);
    }

    public static <A> List<A> appendR(List<A> list1, List<A> list2) {
        return foldr(x -> l -> new Cons<>(x, l), list1, list2);
    }

    public static <A> List<A> concatR(List<List<A>> list) {

        return foldr(x -> y -> append(x, y), list(), list);
    }

    public <B> List<B> mapR(Function<A, B> f) {
        return foldr(list(), h -> t -> new Cons<>(f.apply(h), t));
    }

    public <A> List<A> filterR(Function<A, Boolean> p, List<A> list) {
        return foldr(x -> xs -> p.apply(x) ? xs.cons(x) : xs, list(), list);

    }

    public List<A> takeWhileR(Function<A, Boolean> p) {
        return foldr(list(), x -> ys -> p.apply(x) ? new Cons<>(x, ys) : list());
    }

    public String toStringR() {
        return "";
        //foldrR(x->y->y.append(x).append(","), new Stringbuilder()).append("NIL]").toString();
    }

    public static Integer sumL(List<Integer> list) {

        return foldl(x -> y -> x + y, 0, list);
    }

    public static Double prodL(List<Double> list) {

        return foldl(x -> y -> x * y, 1.0, list);
    }

    public <A> Integer lengthL(List<A> list) {
        return foldl(n -> x -> n + 1, 0, list);

    }


    public boolean elemL(A x) {

        return foldl(y -> f -> (this.head().equals(x)), false);
    }

    public static Boolean andL(List<Boolean> list) {
        return foldl(x -> y -> x && y, true, list);
    }

    public static Boolean orL(List<Boolean> list) {
        return foldl(x -> y -> x || y, false, list);
    }

    public Boolean allL(Function<A, Boolean> p, List<A> list) {
        return foldl(x -> y -> p.apply(y) && x, true, list);
    }

    public Boolean anyL(Function<A, Boolean> p, List<A> list) {
        return foldl(x -> y -> p.apply(y) || x, false, list);
    }

    public A lastL() {
        return foldl(x -> y -> y, null, this);
    }

    public <A> List<A> reverseL(List<A> list) {
        return foldl(xs -> xs::cons, list(), list);
    }

    //Reimplementation all und elem von anyL

    public Boolean allMitAnyL(Function<A, Boolean> p, List<A> list) {
        return !anyL(x -> !p.apply(x), list);
    }

    public Boolean elemMitAnyL(List<A> list, A p) {
        return anyL(x -> x == p, list);
    }
// --------------------------------------------------------------

    //Aufgabe E
    public <B> List<B> concatMap(Function<A, List<B>> f) {
        return foldr(h -> t -> append(f.apply(h), t), list(), this);
    }
    //----------------------------------------------------------------------

    //Aufgabe F
    public static List<Integer> range(int start, int end) {
        return start > end ? list() : new Cons<Integer>(start, range(start + 1, end));
    }

    public static List<String> words(String s) {
        return list(s.split("\\s+"));
    }
    //------------------------------------------------------------------------------

    //Aufgabe G
    public Integer euler1() {
        return sum(range(0, 999).filter(x -> x % 3 == 0 || x % 5 == 0));
    }
    //----------------------------------------------------------------------------------------

    //Aufgaben von ListSet
    //Wurde initialisiert und implementiert in Set
    public Set<A> toSet() {
        return fromList(this);
    }

    public List<A> nub() {

        return fromList(this).toList();
    }

    public <B extends Comparable<B>> Map<B, List<A>> groupBy(Function<A, B> f) {
        return foldr(t -> mt -> {
            final B k = f.apply(t);
            return mt.insert(k, mt.lookup(k).getOrElse(list()).cons(t));
        }, ListMap.empty());
    }

}
//-------------------------------------------------------------------------------------------






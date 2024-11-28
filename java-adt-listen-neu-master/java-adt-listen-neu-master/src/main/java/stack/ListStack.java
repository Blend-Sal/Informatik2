package stack;

import list.List;
import tuple.Tuple;
import static list.List.*;
import static tuple.Tuple.tuple;

public class ListStack<A> implements Stack<A> {
    final private List<A> stack;

    //O von 1
    private ListStack() {
        this.stack = List.list();
    }

    //O von 1
    private ListStack(List<A> list) {
        this.stack = list;
    }

    //O von 1
    public static <A> Stack<A> empty() {
        return new ListStack<>();
    }

    //O von 1
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    //O von 1
    public Stack<A> push(A e) {
        return new ListStack<>(stack.cons(e));
    }

    //O von n
    public Stack<A> pushAll(List<A> xs) {
        return new ListStack<>(append(xs, stack));
    }

    //O von n
    public Stack<A> pushAll(A... es) {
        return pushAll(es);
    }

    //O von 1
    public Stack<A> pop() {
        if (isEmpty()) throw new IllegalStateException("pop from an empty stack");
        return new ListStack<>(stack.tail());
    }

    //O von 1
    public A top() {
        if (isEmpty()) throw new IllegalStateException("top from an empty stack");
        return stack.head();
    }

    //O von 1
    public Tuple<A, Stack<A>> popTop() {
        return tuple(top(), new ListStack<>(stack.tail()));
    }

    // tuple(top(), pop())
    //O von 1
    public List<A> toList() {
        return stack;
    }

    //O von n
    public boolean isEqualTo(Stack<A> s) {
        return stack.equals(s.toList());
    }

    //O von n
    public int size() {
        return stack.length();
    }

    //O von n
    public boolean equals(Object o) {
        return o instanceof ListStack && isEqualTo((Stack<A>) o);
    }

}
// Implementieren Sie auch die Ausnahmebehandlung für den Zugriff auf einen leeren Stack. NICHT VERGESSEN!!!
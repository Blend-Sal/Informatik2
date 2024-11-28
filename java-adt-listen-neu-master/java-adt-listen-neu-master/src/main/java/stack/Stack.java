package stack;

import list.List;
import tuple.Tuple;

public interface Stack<A> {
    //Stack<A> ifEmpty(Stack<A> stack1);
    boolean isEmpty();

    Stack<A> push(A e);

    Stack<A> pushAll(List<A> es);

    Stack<A> pushAll(A... es);

    Stack<A> pop();

    A top();

    Tuple<A, Stack<A>> popTop();

    //Tuple<List<A>,Stack<A>> popTopAll();
    List<A> toList();

    boolean isEqualTo(Stack<A> s);

    int size();

}

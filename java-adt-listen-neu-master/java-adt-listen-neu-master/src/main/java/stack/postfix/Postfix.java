package stack.postfix;

import fpinjava.Function;
import list.List;
import stack.Stack;

import static fpinjava.Function.flip;
import static list.List.words;
import static stack.ListStack.empty;


public class Postfix {

    static final Function<Double, Function<Double, Double>> add = x -> y -> x + y;

    static final Function<Double, Function<Double, Double>> sub = x -> y -> x - y;

    static final Function<Double, Function<Double, Double>> mul = x -> y -> x * y;

    static final Function<Double, Function<Double, Double>> div = x -> y -> x / y;

    static final Function<Double, Function<Double, Double>> pow = x -> y -> Math.pow(x, y);
    static final Function<Double, Double> fact = n -> (n >= 1) ? n * Postfix.fact.apply(n - 1) : 1;


    static double eval_(Stack<Double> s, List<String> expr) {
        switch (expr.head()) {
            case "-":
                return bin(flip(sub), s, expr);
            case "+":
                return bin(add, s, expr);
            case "!":
                return un(fact, s, expr);
            case "^":
                return bin(flip(pow), s, expr);
            case "*":
                return bin(mul, s, expr);
            case "/":
                return bin(flip(div), s, expr);
            case "NIL":
                break;
            default:
                return eval_(s.push(Double.parseDouble(expr.head())), expr.tail());
        }
        return 0;
    }

    static double eval(String expr) {
        return expr.isEmpty() ? 0.0 : eval_(empty(), words(expr));
    }


    public static double bin(Function<Double, Function<Double, Double>> f, Stack<Double> s, List<String> expr) {
        return expr.tail().isEmpty() ? f.apply(s.top()).apply(s.pop().top()) : eval_(s.pop().pop().push(f.apply(s.top()).apply(s.pop().top())), expr.tail());
    }

    public static double un(Function<Double, Double> f, Stack<Double> s, List<String> expr) {
        return expr.tail().isEmpty() ? f.apply(s.top()) : eval_(s.pop().push(f.apply(s.top())), expr.tail());
    }

}

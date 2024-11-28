package stack.postfix;
import net.jqwik.api.*;

import static stack.postfix.Postfix.eval;

public class PostfixTest {
    @Data
    Iterable<Tuple.Tuple2<String, Double>> dataForPostfix() {
        return Table.of(
                Tuple.of("3 4 +", 7.0),
                Tuple.of("2 6 -", -4.0),
                Tuple.of("10 4 3 + 2 * -", -4.0),
                Tuple.of("90 34 12 33 55 66 + * - + -", 4037.0),
                Tuple.of("2 2 3 ^ ^", 256.0),
                Tuple.of("3 ! 4 +", 10.0),
                Tuple.of("4 12 + 4 4 * /", 1.0),
                Tuple.of("2 3 ^", 8.0)
        );
    }

    @Property
    @FromData("dataForPostfix")
    boolean data_postfix(@ForAll String s, @ForAll double result) {
        return eval(s) == result;
    }
}

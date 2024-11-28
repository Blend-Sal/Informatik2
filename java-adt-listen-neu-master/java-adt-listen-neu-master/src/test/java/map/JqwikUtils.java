package map;

import list.List;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.apache.commons.lang3.SerializationUtils;
import tuple.Tuple;

import java.io.Serializable;
import static list.JqwikUtils.lists;
import static list.JqwikUtils.unzip;
import static list.JqwikUtils.zip;
import static tuple.Tuple.tuple;

public class JqwikUtils {
  public static <A,B> Arbitrary<Tuple<A,B>> tuples(Arbitrary<A> ks, Arbitrary<B> vs){
    return Combinators.combine(ks,vs).as((k,v) -> tuple(k,v));
  }

  public static <A,B> Arbitrary<List<Tuple<A,B>>> zips(Arbitrary<List<A>> xsa, Arbitrary<List<B>> ysa){
    return Combinators.combine(xsa, ysa).as((xs,ys) -> zip(xs,ys));
  }

  public static <A,B> Arbitrary<List<Tuple<A,B>>> tupleLists(Arbitrary<A> ks, Arbitrary<B> vs, int minLen, int maxLen){
    return lists(tuples(ks,vs),minLen,maxLen);
  }

  public static <A,B> Arbitrary<List<Tuple<A,B>>> tupleLists(Arbitrary<A> ks, Arbitrary<B> vs, int maxLen){
    return tupleLists(ks,vs,0,maxLen);
  }

  public static <A,B> Arbitrary<List<Tuple<A,B>>> tupleLists2(Arbitrary<A> ks, Arbitrary<B> vs, int minLen, int maxLen){
    return zips(lists(ks,minLen,maxLen), lists(vs,minLen,maxLen));
  }

  public static <A extends Serializable,B extends Serializable>
  Arbitrary<Tuple<Tuple<A,B>,Tuple<A,B>>>
  duplicate(Arbitrary<Tuple<A,B>> ta){
    return ta.map(t->tuple(t, tuple(SerializationUtils.clone(t.fst),SerializationUtils.clone(t.snd))));
  }

  public static <A extends Serializable,B extends Serializable>
  Arbitrary<Tuple<List<Tuple<A,B>>,List<Tuple<A,B>>>>
  equalTupleLists(Arbitrary<A> ks, Arbitrary<B> vs, int maxLen){
    return lists(duplicate(tuples(ks,vs)),maxLen).map(xs->unzip(xs));
  }

  public static <A extends Serializable,B extends Serializable>
  Arbitrary<Tuple<Tuple<A,B>,Tuple<A,B>>>
  sameKeysDiffVals(Arbitrary<A> ks, Arbitrary<B> vs){
    Arbitrary<Tuple<A,A>> kst = list.JqwikUtils.duplicate(ks);
    return Combinators.combine(kst,vs).as((kt,v) -> tuple(tuple(kt.fst,v),tuple(kt.snd,v)));
  }

  public static <A,B> Arbitrary<Tuple<List<Tuple<A,B>>,List<Tuple<A,B>>>>
  tupleListsWithSameAsDiffBs(Arbitrary<A> xa, Arbitrary<B> ya1, Arbitrary<B> ya2, int maxLen){
    final var xsa = lists(xa,maxLen);
    final var ysa1 = lists(ya1,maxLen);
    final var ysa2 = lists(ya2,maxLen);

    return xsa.flatMap(
            xs->ysa1.flatMap(
                    ys1->ysa2.flatMap(
                            ys2->Arbitraries.of(zip(xs,ys1)).flatMap(
                                    l1->Arbitraries.of(zip(xs,ys2)).map(
                                            l2->tuple(l1,l2)
                                    )
                            )
                    )
            )
    );
  }
  /* Mit Haskell-QuickCheck geht das natürlich viel übersichtlicher
    tupleListsWithSameAsDiffBs :: Gen a -> Gen b -> Gen b -> Int -> Gen ([(a,b)],[(a,b)])
    tupleListsWithSameAsDiffBs xa ya1 ya2 maxLen = do
      xs  <- resize maxLen (listOf xa)
      ys1 <- resize maxLen (listOf ya1)
      ys2 <- resize maxLen (listOf ya2)
      return $ ((zip xs ys1),(zip xs ys2))
   */
}

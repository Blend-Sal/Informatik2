package queue;

public class TwoListQJqwikTest extends ADTQueueJqwikTest {

	@Override
	protected <A> Queue<A> empty(){
		return TwoListQ.empty();
	}
}

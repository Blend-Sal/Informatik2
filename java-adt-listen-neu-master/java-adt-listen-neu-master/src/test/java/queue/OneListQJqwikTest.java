package queue;

public class OneListQJqwikTest extends ADTQueueJqwikTest {

	@Override
	protected <A> Queue<A> empty(){
		return OneListQ.empty();
	}
}

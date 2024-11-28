package queue;

import list.List;

import static list.List.list;

public class TwoListQ<A> implements Queue<A> {

	private TwoListQ(){
	}

	public static <A> Queue<A> empty() {
		return new TwoListQ<>();
	}

	@Override
	public Queue<A> enQueueAll(List<A> xs) {
		return new TwoListQ<>();
	}


	@Override
	public List<A> toList() {
		return list();
	}

	@Override
	public boolean isEqualTo(Queue<A> q){
		return false;
	}


}

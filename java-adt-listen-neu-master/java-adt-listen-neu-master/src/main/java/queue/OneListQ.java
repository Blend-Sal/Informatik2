package queue;

import list.List;

import static list.List.list;

public class OneListQ<A> implements Queue<A> {

	private OneListQ(){
	}

	public static <A> Queue<A> empty() {
		return new OneListQ<>();
	}

	@Override
	public Queue<A> enQueueAll(List<A> xs) {
		return new OneListQ<>();
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

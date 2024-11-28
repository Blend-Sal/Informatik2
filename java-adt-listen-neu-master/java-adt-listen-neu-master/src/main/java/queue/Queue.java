package queue;

import list.List;
import tuple.Tuple;

public interface Queue<A> {
  // Hier die Methoden des ADTs Queue definieren.



  // Diese Methoden nicht löschen.
  // Sie werden in den folgenden Aufgaben gefordert.
	boolean isEqualTo(Queue<A> q);
	Queue<A> enQueueAll(List<A> es);
	List<A> toList();
}

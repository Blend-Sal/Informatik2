package map;


public class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {

    public final K KEY;

    public final V VALUE;

    public Entry(K key, V value) {
        this.KEY = key;
        this.VALUE = value;
    }

    public Entry(K key) {
        this.KEY = key;
        this.VALUE = null;
    }


    public static <K extends Comparable<K>, V> Entry<K, V> entry(K key, V value) {
        return new Entry<K, V>(key, value);
    }

    public static <K extends Comparable<K>, V> Entry<K, V> entry(K key) {
        return new Entry<K, V>(key);
    }


    @Override
    public int compareTo(Entry<K, V> other) {
        return this.KEY.compareTo(other.KEY);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Entry && isEqualTo((Entry) o);
    }

    private boolean isEqualTo(Entry<K, V> o) {
        return o.KEY.equals(this.KEY);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", KEY, VALUE);
    }


}

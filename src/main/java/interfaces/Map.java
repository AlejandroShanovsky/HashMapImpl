package interfaces;

public interface Map<K, V> extends Iterable<V> {
    boolean put(K key, V value);
    V get(K key);
    int size();
}

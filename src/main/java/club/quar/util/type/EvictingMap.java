package club.quar.util.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A map which automatically evicts elements from the head of the queue when
 * attempting to add new elements onto the queue and it is full. This queue orders elements FIFO
 * (first-in-first-out). This data structure is logically equivalent to a circular buffer (i.e.,
 * cyclic buffer or ring buffer).
 */
@RequiredArgsConstructor
public final class EvictingMap<K, V> extends HashMap<K, V> {

    @Getter
    private final int size;
    private final Deque<K> storedKeys = new LinkedList<>();

    @Override
    public boolean remove(final Object key, final Object value) {
        storedKeys.remove(key);
        return super.remove(key, value);
    }

    @Override
    public V putIfAbsent(final K key, final V value) {
        if (!storedKeys.contains(key) || !get(key).equals(value))
            checkAndRemove();
        return super.putIfAbsent(key, value);
    }

    @Override
    public V put(final K key, final V value) {
        checkAndRemove();
        storedKeys.addLast(key);
        return super.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        storedKeys.clear();
        super.clear();
    }

    @Override
    public V remove(final Object key) {
        storedKeys.remove(key);
        return super.remove(key);
    }

    private boolean checkAndRemove() {
        if (storedKeys.size() >= size) {
            final K key = storedKeys.removeFirst();

            remove(key);
            return true;
        }

        return false;
    }
}

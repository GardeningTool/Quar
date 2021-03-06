package club.quar.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An abstract manager class used by manager subclasses.
 *
 * @param <T> The type of the manager.
 */
public abstract class Manager<T> {

    private final List<T> list = new ArrayList<>();

    @SafeVarargs
    public final void add(T... objects) {
        list.addAll(Arrays.asList(objects));
    }

    public int size() {
        return list.size();
    }

    public void removeIf(Predicate<T> predicate) {
        list.removeIf(predicate);
    }

    public List<T> getIf(Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<T> getAll() {
        return list;
    }

}

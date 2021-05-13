package club.quar.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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

}

package patterns;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorSortingStrategy<T> implements SortingStrategy<T> {
    private final Comparator<T> comparator;

    public ComparatorSortingStrategy(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<T> sort(List<T> items) {
        List<T> result = new ArrayList<>();
        if (items == null) {
            return result;
        }
        for (T item : items) {
            if (item != null) {
                result.add(item);
            }
        }
        if (comparator != null) {
            result.sort(comparator);
        }
        return result;
    }
}

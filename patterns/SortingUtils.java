package patterns;

import comparators.UniversityComparators;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import models.users.Student;

public class SortingUtils {
     public static List<Student> sortStudents(List<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T> List<T> sort(List<T> items, SortingStrategy<T> strategy) {
        if (strategy == null) {
            return items;
        }
        return strategy.sort(items);
    }
    
}

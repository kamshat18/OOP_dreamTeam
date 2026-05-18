package patterns;
import comparators.UniversityComparators;
import models.ResearchPaper;
import models.Student;
import models.Teacher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingUtils {
     public static List<Student> sortStudents(List<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }
    //дальше написать надо
    
}

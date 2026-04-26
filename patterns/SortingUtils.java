package OOP_dreamTeam.patterns;
import OOP_dreamTeam.comparators.UniversityComparators;
import OOP_dreamTeam.models.ResearchPaper;
import OOP_dreamTeam.models.Student;
import OOP_dreamTeam.models.Teacher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingUtils {
     public static List<Student> sortStudents(List<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }
    //дальше написать надо
    
}

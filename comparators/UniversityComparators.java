package OOP_dreamTeam.comparators;

import OOP_dreamTeam.
models.ResearchPaper;
import OOP_dreamTeam.models.Student;
import OOP_dreamTeam.models.Teacher;

import java.util.Comparator;

public class UniversityComparators {
    public static final Comparator<Student> BY_GPA_DESC = Comparator.comparingDouble(Student::getGpa).reversed();
    public static final Comparator<Student> BY_GPA_ASC = Comparator.comparingDouble(Student::getGpa);

    // дальше нужно добавить компараторы для других полей студентов, а также для учителей и научных работ
}

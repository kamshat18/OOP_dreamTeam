package comparators;

import models.ResearchPaper;
import models.Student;
import models.Teacher;

import java.util.Comparator;

public class UniversityComparators {
    public static final Comparator<Student> BY_GPA_DESC = Comparator.comparingDouble(Student::getGpa).reversed();
    public static final Comparator<Student> BY_GPA_ASC = Comparator.comparingDouble(Student::getGpa);

    // дальше нужно добавить компараторы для других полей студентов, а также для учителей и научных работ
}

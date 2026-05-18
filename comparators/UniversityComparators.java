package comparators;

import java.util.Comparator;
import models.research.ResearchPaper;
import models.users.Student;
import models.users.Teacher;

public class UniversityComparators {
    public static final Comparator<Student> BY_GPA_DESC = Comparator.comparingDouble(Student::getGpa).reversed();
    public static final Comparator<Student> BY_GPA_ASC = Comparator.comparingDouble(Student::getGpa);
    public static final Comparator<Student> BY_NAME = Comparator.comparing(Student::getFullName);
    public static final Comparator<Student> BY_ID = Comparator.comparing(Student::getStudentId);

    public static final Comparator<Teacher> TEACHER_BY_NAME = Comparator.comparing(Teacher::getFullName);
    public static final Comparator<Teacher> TEACHER_BY_POSITION = Comparator.comparing(Teacher::getPosition);

    public static final Comparator<ResearchPaper> PAPER_BY_CITATIONS_DESC =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();
    public static final Comparator<ResearchPaper> PAPER_BY_DATE_DESC =
            Comparator.comparing(ResearchPaper::getPublicationDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed();
    public static final Comparator<ResearchPaper> PAPER_BY_PAGES_DESC =
            Comparator.comparingInt(ResearchPaper::getPages).reversed();
}

package models;

import java.util.List;

public class AcademicPerformanceReportGenerator extends AbstractReportGenerator {
    private final List<Student> students;

    public AcademicPerformanceReportGenerator(List<Student> students) {
        this.students = students;
    }

    @Override
    public Report generate() {
        if (students == null || students.isEmpty()) {
            return new Report("Academic Performance Report", "No students");
        }
        double sum = 0;
        double min = students.get(0).getGpa();
        double max = students.get(0).getGpa();
        for (Student student : students) {
            double curGpa = student.getGpa();
            sum += curGpa;
            if (curGpa < min) min = curGpa;
            if (curGpa > max) max = curGpa;
        }
        String content =
                "Total number of students: " + students.size() + "\n" +
                        "Average GPA: " + sum / students.size() + "\n" +
                        "Maximum GPA: " + max + "\n" +
                        "Minimum GPA: " + min + "\n";
        return new Report("Academic Performance Report", content);
    }
}

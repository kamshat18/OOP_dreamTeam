import comparators.UniversityComparators;
import enums.*;
import exceptions.NotResearcherException;
import exceptions.SupervisorException;
import interfaces.*;
import models.*;
import patterns.*;

import java.io.File;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static DataStorage storage = DataStorage.getInstance();

    public static void main(String[] args) {
        System.out.println("+======================================+");
        System.out.println("|   UNIVERSITY INFORMATION SYSTEM      |");
        System.out.println("+======================================+");
        
        seedData();
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if (currentUser instanceof Admin) {
                showAdminMenu((Admin) currentUser);
            } else if (currentUser instanceof Teacher) {
                showTeacherMenu((Teacher) currentUser);
            } else if (currentUser instanceof Student) {
                showStudentMenu((Student) currentUser);
            } else if (currentUser instanceof Manager) {
                showManagerMenu((Manager) currentUser);
            } else if (currentUser instanceof TechSupportSpecialist) {
                showTechSupportMenu((TechSupportSpecialist) currentUser);
            }
        }
    }

    // =========================================================
    // LOGIN MENU
    // =========================================================
    private static void showLoginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Login: ");
        String login = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        AuthenticationService auth = new AuthenticationService();
        Optional<User> user = auth.login(login, password);
        if (user.isPresent()) {
            currentUser = user.get();
            System.out.println("\nWelcome, " + currentUser.getFullName() + "!");
        } else {
            System.out.println("Invalid login or password. Please try again.");
        }
    }

    // =========================================================
    // ADMIN MENU
    // =========================================================
    private static void showAdminMenu(Admin admin) {
        System.out.println("\n+-- ADMIN MENU --+");
        System.out.println("  1. Add user");
        System.out.println("  2. Remove user");
        System.out.println("  3. View all users");
        System.out.println("  4. Send message");
        System.out.println("  5. View inbox");
        System.out.println("  6. Generate system report");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.print("Type (student/teacher/manager/admin/tech): ");
                String type = scanner.nextLine().trim();
                System.out.print("ID: ");
                String id = scanner.nextLine().trim();
                System.out.print("Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Login: ");
                String login = scanner.nextLine().trim();
                System.out.print("Password: ");
                String password = scanner.nextLine().trim();
                
                User newUser = createUserByType(type, id, name, login, password);
                if (newUser != null) {
                    admin.addUser(newUser);
                    storage.addUser(newUser);
                    System.out.println("User added successfully!");
                }
            }
            case "2" -> {
                System.out.print("Login of user to remove: ");
                admin.removeUser(scanner.nextLine().trim());
            }
            case "3" -> admin.viewAllUsers();
            case "4" -> sendMessageMenu(admin);
            case "5" -> admin.viewInbox();
            case "6" -> System.out.println(admin.generateSystemReport());
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // =========================================================
    // TEACHER MENU
    // =========================================================
    private static void showTeacherMenu(Teacher teacher) {
        System.out.println("\n+-- TEACHER MENU --+");
        System.out.println("  1. My courses");
        System.out.println("  2. Put mark for student");
        System.out.println("  3. View students in course");
        System.out.println("  4. My research papers");
        System.out.println("  5. Add research paper");
        System.out.println("  6. View my rating");
        System.out.println("  7. Send complaint to dean");
        System.out.println("  8. Send message");
        System.out.println("  9. View inbox");
        System.out.println(" 10. View news");
        System.out.println(" 11. Mark attendance");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.println("=== My Courses ===");
                teacher.getCourses().forEach(c -> System.out.println("  " + c.getTitle() + " (" + c.getCourseCode() + ")"));
            }
            case "2" -> {
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findCourseByCode(courseCode).ifPresentOrElse(course -> {
                    System.out.print("Student login: ");
                    findByLogin(scanner.nextLine().trim()).ifPresentOrElse(u -> {
                        if (!(u instanceof Student)) {
                            System.out.println("Not a student.");
                            return;
                        }
                        Student s = (Student) u;
                        try {
                            System.out.print("First attestation (0-30): ");
                            double att1 = Double.parseDouble(scanner.nextLine());
                            System.out.print("Second attestation (0-30): ");
                            double att2 = Double.parseDouble(scanner.nextLine());
                            System.out.print("Final exam (0-40): ");
                            double finalExam = Double.parseDouble(scanner.nextLine());
                            Mark mark = new Mark(att1, att2, finalExam);
                            teacher.putMark(s, course, mark);
                            System.out.println("Grade assigned!");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number format.");
                        }
                    }, () -> System.out.println("Student not found."));
                }, () -> System.out.println("Course not found."));
            }
            case "3" -> {
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findCourseByCode(courseCode).ifPresentOrElse(course -> {
                    System.out.println("=== Students in " + course.getTitle() + " ===");
                    // Assuming Course has getStudents() method
                }, () -> System.out.println("Course not found."));
            }
            case "4" -> {
                System.out.println("Sort by: 1-citations, 2-date, 3-pages");
                System.out.print("Choice: ");
                String choice = scanner.nextLine().trim();
                Comparator<ResearchPaper> comp = switch (choice) {
                    case "2" -> UniversityComparators.PAPER_BY_DATE_DESC;
                    case "3" -> UniversityComparators.PAPER_BY_PAGES_DESC;
                    default -> UniversityComparators.PAPER_BY_CITATIONS_DESC;
                };
                teacher.printPapers(comp);
            }
            case "5" -> {
                System.out.print("Title: ");
                String title = scanner.nextLine().trim();
                System.out.print("Journal: ");
                String journal = scanner.nextLine().trim();
                System.out.print("DOI: ");
                String doi = scanner.nextLine().trim();
                System.out.print("Citations: ");
                try {
                    int citations = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Pages: ");
                    int pages = Integer.parseInt(scanner.nextLine().trim());
                    ResearchPaper paper = new ResearchPaper(title, List.of(teacher.getFullName()),
                            journal, pages, citations, new Date(), doi);
                    teacher.publishPaper(paper);
                    System.out.println("Paper added! H-index: " + teacher.calculateHIndex());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
            case "6" -> System.out.println("Your rating: " + TeacherRating.getRating(teacher));
            case "7" -> {
                System.out.print("Student login: ");
                String studentLogin = scanner.nextLine().trim();
                findByLogin(studentLogin).ifPresentOrElse(u -> {
                    if (!(u instanceof Student)) {
                        System.out.println("Not a student.");
                        return;
                    }
                    System.out.print("Complaint text: ");
                    String text = scanner.nextLine().trim();
                    System.out.print("Urgency level (LOW/MEDIUM/HIGH): ");
                    try {
                        UrgencyLevel level = UrgencyLevel.valueOf(scanner.nextLine().trim().toUpperCase());
                        teacher.sendComplaintToDean(List.of((Student) u), text, level);
                        System.out.println("Complaint sent!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid urgency level.");
                    }
                }, () -> System.out.println("Student not found."));
            }
            case "8" -> sendMessageMenu(teacher);
            case "9" -> teacher.viewInbox();
            case "10" -> getNews().forEach(System.out::println);
            case "11" -> markAttendanceMenu(teacher);
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // =========================================================
    // STUDENT MENU
    // =========================================================
    private static void showStudentMenu(Student student) {
        System.out.println("\n+-- STUDENT MENU --+");
        System.out.println("  1. View available courses");
        System.out.println("  2. Register for course");
        System.out.println("  3. My courses");
        System.out.println("  4. View transcript");
        System.out.println("  5. Rate teacher");
        System.out.println("  6. View news");
        System.out.println("  7. Join organization");
        System.out.println("  8. Switch language");
        System.out.println("  9. View teacher info");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.println("=== Available Courses ===");
                getCourses().forEach(c -> System.out.printf("  %s - %s (%d credits)%n", 
                    c.getCourseCode(), c.getTitle(), c.getCredits()));
            }
            case "2" -> {
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findCourseByCode(courseCode).ifPresentOrElse(
                    student::addCourse,
                    () -> System.out.println("Course not found."));
            }
            case "3" -> {
                System.out.println("=== My Courses ===");
                student.getCourses().forEach(c -> System.out.println("  " + c.getTitle()));
            }
            case "4" -> {
                Transcript transcript = student.viewTranscript();
                System.out.println("GPA: " + transcript.getGpa());
            }
            case "5" -> {
                System.out.print("Teacher login: ");
                findByLogin(scanner.nextLine().trim()).ifPresentOrElse(u -> {
                    if (!(u instanceof Teacher)) {
                        System.out.println("Not a teacher.");
                        return;
                    }
                    System.out.print("Rating (1-5): ");
                    try {
                        int rating = Integer.parseInt(scanner.nextLine().trim());
                        student.rateTeacher((Teacher) u, rating);
                        System.out.println("Rating submitted!");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                    }
                }, () -> System.out.println("Teacher not found."));
            }
            case "6" -> getNews().forEach(System.out::println);
            case "7" -> {
                System.out.print("Organization ID: ");
                String orgId = scanner.nextLine().trim();
                findOrganizationById(orgId).ifPresentOrElse(
                    student::joinOrganization,
                    () -> System.out.println("Organization not found."));
            }
            case "8" -> {
                System.out.print("Language (EN/RU/KZ): ");
                try {
                    Language lang = Language.valueOf(scanner.nextLine().trim().toUpperCase());
                    student.switchLanguage(lang);
                    System.out.println("Language switched to " + student.getLanguage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid language.");
                }
            }
            case "9" -> {
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findCourseByCode(courseCode).ifPresentOrElse(course -> {
                    System.out.print("Lesson type (LECTURE/PRACTICE): ");
                    try {
                        LessonType type = LessonType.valueOf(scanner.nextLine().trim().toUpperCase());
                        System.out.println(student.viewTeacherInfo(course, type));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid lesson type.");
                    }
                }, () -> System.out.println("Course not found."));
            }
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // =========================================================
    // MANAGER MENU
    // =========================================================
    private static void showManagerMenu(Manager manager) {
        System.out.println("\n+-- MANAGER MENU --+");
        System.out.println("  1. Add course");
        System.out.println("  2. Assign teacher to course");
        System.out.println("  3. Approve student registration");
        System.out.println("  4. View students by GPA");
        System.out.println("  5. View students by name");
        System.out.println("  6. View all teachers");
        System.out.println("  7. Generate report");
        System.out.println("  8. Publish news");
        System.out.println("  9. View news");
        System.out.println(" 10. Send message");
        System.out.println(" 11. View inbox");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.print("Course code: ");
                String code = scanner.nextLine().trim();
                System.out.print("Title: ");
                String title = scanner.nextLine().trim();
                System.out.print("Credits: ");
                try {
                    int credits = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("School: ");
                    String school = scanner.nextLine().trim();
                    System.out.print("Year: ");
                    int year = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Course type (MAJOR/MINOR/FREE_ELECTIVE): ");
                    CourseType type = CourseType.valueOf(scanner.nextLine().trim().toUpperCase());
                    Course course = new Course(code, title, credits, school, year, type);
                    manager.addCourseForRegistration(course, year, school);
                    storage.addCourse(course);
                    System.out.println("Course added!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid course type.");
                }
            }
            case "2" -> {
                System.out.print("Teacher login: ");
                String teacherLogin = scanner.nextLine().trim();
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                System.out.print("Lesson type (LECTURE/PRACTICE): ");
                try {
                    LessonType type = LessonType.valueOf(scanner.nextLine().trim().toUpperCase());
                    findByLogin(teacherLogin).ifPresentOrElse(u -> {
                        if (!(u instanceof Teacher)) {
                            System.out.println("Not a teacher.");
                            return;
                        }
                        findCourseByCode(courseCode).ifPresentOrElse(course -> {
                            manager.assignCourseToTeacher(course, (Teacher) u, type);
                            System.out.println("Teacher assigned!");
                        }, () -> System.out.println("Course not found."));
                    }, () -> System.out.println("Teacher not found."));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid lesson type.");
                }
            }
            case "3" -> {
                System.out.print("Student login: ");
                String studentLogin = scanner.nextLine().trim();
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findByLogin(studentLogin).ifPresentOrElse(u -> {
                    if (!(u instanceof Student)) {
                        System.out.println("Not a student.");
                        return;
                    }
                    findCourseByCode(courseCode).ifPresentOrElse(course -> {
                        manager.approveRegistration((Student) u, course);
                        System.out.println("Registration approved!");
                    }, () -> System.out.println("Course not found."));
                }, () -> System.out.println("Student not found."));
            }
            case "4" -> {
                List<Student> students = manager.viewAllStudents(SortBy.GPA);
                students.forEach(s -> System.out.printf("  %s - GPA: %.2f%n", s.getFullName(), s.getGpa()));
            }
            case "5" -> {
                List<Student> students = manager.viewAllStudents(SortBy.NAME);
                students.forEach(s -> System.out.println("  " + s.getFullName()));
            }
            case "6" -> manager.viewAllTeachers();
            case "7" -> System.out.println(manager.generateStatisticalReport());
            case "8" -> {
                System.out.print("Title: ");
                String title = scanner.nextLine().trim();
                System.out.print("Content: ");
                String content = scanner.nextLine().trim();
                News news = new News(title, content, NewsType.NORMAL);
                manager.manageNews(news, "add");
                System.out.println("News published!");
            }
            case "9" -> manager.viewNews();
            case "10" -> sendMessageMenu(manager);
            case "11" -> manager.viewInbox();
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // =========================================================
    // TECH SUPPORT MENU
    // =========================================================
    private static void showTechSupportMenu(TechSupportSpecialist support) {
        System.out.println("\n+-- TECH SUPPORT MENU --+");
        System.out.println("  1. View all requests");
        System.out.println("  2. Update request status");
        System.out.println("  3. Send message");
        System.out.println("  4. View inbox");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> support.viewRequests().forEach(System.out::println);
            case "2" -> {
                System.out.print("Request ID: ");
                String requestId = scanner.nextLine().trim();
                findRequestById(requestId).ifPresentOrElse(request -> {
                    System.out.println("Current status: " + request.getStatus());
                    System.out.print("New status (PENDING/ACCEPTED/REJECTED/DONE/VIEWED): ");
                    try {
                        RequestStatus status = RequestStatus.valueOf(scanner.nextLine().trim().toUpperCase());
                        support.updateRequestStatus(request, status);
                        System.out.println("Status updated!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status.");
                    }
                }, () -> System.out.println("Request not found."));
            }
            case "3" -> sendMessageMenu(support);
            case "4" -> support.viewInbox();
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // =========================================================
    // HELPER METHODS (using your existing storage)
    // =========================================================
    
    private static Optional<User> findByLogin(String login) {
        return storage.getUsers().stream()
            .filter(u -> u.getEmail().equals(login))
            .findFirst();
    }
    
    private static Optional<Course> findCourseByCode(String code) {
        return storage.getCourses().stream()
            .filter(c -> c.getCourseCode().equals(code))
            .findFirst();
    }
    
    private static Optional<Organization> findOrganizationById(String id) {
        // You would need to get organizations from your storage
        return Optional.empty();
    }
    
    private static Optional<Request> findRequestById(String id) {
        return storage.getRequests().stream()
            .filter(r -> r.getRequestId().equals(id))
            .findFirst();
    }
    
    private static List<Course> getCourses() {
        return storage.getCourses();
    }
    
    private static List<News> getNews() {
        return storage.getNews();
    }
    
    private static void markAttendanceMenu(Teacher teacher) {
        System.out.print("Course code: ");
        String courseCode = scanner.nextLine().trim();
        findCourseByCode(courseCode).ifPresentOrElse(course -> {
            System.out.print("Student login: ");
            findByLogin(scanner.nextLine().trim()).ifPresentOrElse(u -> {
                if (!(u instanceof Student)) {
                    System.out.println("Not a student.");
                    return;
                }
                System.out.print("Lesson type (LECTURE/PRACTICE): ");
                try {
                    LessonType type = LessonType.valueOf(scanner.nextLine().trim().toUpperCase());
                    System.out.print("Status (PRESENT/ABSENT/LATE/EXCUSED): ");
                    AttendanceStatus status = AttendanceStatus.valueOf(scanner.nextLine().trim().toUpperCase());
                    // Find lesson by type
                    course.getLessons().stream()
                        .filter(l -> l.getLessonType() == type)
                        .findFirst()
                        .ifPresent(lesson -> {
                            AttendanceService service = new AttendanceService();
                            service.markAttendance((Student) u, lesson, status);
                            System.out.println("Attendance marked!");
                        });
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input.");
                }
            }, () -> System.out.println("Student not found."));
        }, () -> System.out.println("Course not found."));
    }
    
    private static void sendMessageMenu(Employee sender) {
        System.out.print("Recipient login: ");
        findByLogin(scanner.nextLine().trim()).ifPresentOrElse(recipient -> {
            if (!(recipient instanceof Employee)) {
                System.out.println("Recipient is not an employee.");
                return;
            }
            System.out.print("Message: ");
            String text = scanner.nextLine().trim();
            sender.sendMessage((Employee) recipient, text);
            System.out.println("Message sent!");
        }, () -> System.out.println("User not found."));
    }
    
    private static User createUserByType(String type, String id, String name, String login, String password) {
        Date now = new Date();
        return switch (type.toLowerCase()) {
            case "student" -> new Student(id, name, login, password, "EN", "S-" + id, "SITE", 1, 0.0, 0, new ArrayList<>(), new ArrayList<>());
            case "teacher" -> new Teacher(id, name, login, password, "EN", 300000, now, "EMP-" + id, "T-" + id, TeacherPosition.LECTURER, new ArrayList<>());
            case "manager" -> new Manager(id, name, login, password, "EN", 400000, now, "MAN-" + id, ManagerType.DEPARTMENT_MANAGER, "SITE");
            case "admin" -> new Admin(id, name, login, password, "EN", 500000, now, "ADM-" + id);
            case "tech" -> new TechSupportSpecialist(id, name, login, password, "EN", 350000, now, "SUP-" + id);
            default -> null;
        };
    }
    
    private static void logout() {
        System.out.println("Goodbye, " + currentUser.getFullName() + "!");
        currentUser = null;
    }
    
    // =========================================================
    // SEED DATA (using your existing classes)
    // =========================================================
    private static void seedData() {
        Date now = new Date();
        
        if (!storage.getUsers().isEmpty()) return;
        
        Admin admin = new Admin("a1", "Admin", "admin", "admin123", "EN", 500000, now, "ADM-1");
        Teacher teacher = new Teacher("t1", "Professor Ada", "ada", "ada123", "EN", 600000, now, "EMP-T1", "T-1", TeacherPosition.PROFESSOR, new ArrayList<>());
        Student student = new Student("s1", "Bob", "bob", "bob123", "EN", "S-1", "SITE", 2, 3.6, 0, new ArrayList<>(), new ArrayList<>());
        Student student2 = new Student("s2", "Alice", "alice", "alice123", "EN", "S-2", "SITE", 2, 3.95, 0, new ArrayList<>(), new ArrayList<>());
        Manager manager = new Manager("m1", "Manager Asel", "asel", "asel123", "EN", 450000, now, "MAN-1", ManagerType.DEPARTMENT_MANAGER, "SITE");
        TechSupportSpecialist support = new TechSupportSpecialist("ts1", "Tech Support", "support", "support123", "EN", 300000, now, "SUP-1");
        
        storage.addUser(admin);
        storage.addUser(teacher);
        storage.addUser(student);
        storage.addUser(student2);
        storage.addUser(manager);
        storage.addUser(support);
        
        Course course = new Course("CS101", "Object-Oriented Programming", 5, "SITE", 2, CourseType.MAJOR);
        storage.addCourse(course);
        
        System.out.println("=== Test data loaded ===");
        System.out.println("Logins: admin/admin123 | ada/ada123 | bob/bob123 | alice/alice123 | asel/asel123 | support/support123");
    }
}
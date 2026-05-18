import comparators.UniversityComparators;
import enums.AttendanceStatus;
import enums.CourseType;
import enums.Language;
import enums.LessonType;
import enums.ManagerType;
import enums.NewsType;
import enums.RequestStatus;
import enums.SortBy;
import enums.TeacherPosition;
import enums.UrgencyLevel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import models.academic.AttendanceService;
import models.academic.Course;
import models.academic.Lesson;
import models.academic.Mark;
import models.academic.TeacherRating;
import models.academic.Transcript;
import models.messaging.Message;
import models.messaging.OfficialMessage;
import models.news.News;
import models.news.NewsGenerator;
import models.organization.Organization;
import models.organization.Request;
import models.research.ResearchAnalytics;
import models.research.ResearchPaper;
import models.users.Admin;
import models.users.Employee;
import models.users.Manager;
import models.users.ResearchEmployee;
import models.users.Student;
import models.users.Teacher;
import models.users.TechSupportSpecialist;
import models.users.User;
import patterns.CommandInvoker;
import patterns.ComparatorSortingStrategy;
import patterns.DataStorage;
import patterns.RequestStatusCommand;
import patterns.SortingStrategy;
import patterns.SortingUtils;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static DataStorage storage = DataStorage.getInstance();
    private static final AttendanceService attendanceService = new AttendanceService();
    private static final CommandInvoker commandInvoker = new CommandInvoker();

    public static void main(String[] args) {
        System.out.println("+===================================+");
        System.out.println("|   UNIVERSITY INFORMATION SYSTEM   |");
        System.out.println("+===================================+");

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

    // LOGIN MENU
    private static void showLoginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Login: ");
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        String login = scanner.nextLine().trim();
        System.out.print("Password: ");
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        String password = scanner.nextLine().trim();

        Optional<User> user = storage.getUsers().stream()
                .filter(u -> u.login(login, password))
                .findFirst();
        if (user.isPresent()) {
            currentUser = user.get();
            System.out.println("\nWelcome, " + currentUser.getFullName() + "!");
        } else {
            System.out.println("Invalid login or password. Please try again.");
        }
    }

    // ADMIN MENU
    private static void showAdminMenu(Admin admin) {
        System.out.println("\n+-- ADMIN MENU --+");
        System.out.println("  1. Add user");
        System.out.println("  2. Remove user");
        System.out.println("  3. View all users");
        System.out.println("  4. Send message");
        System.out.println("  5. View inbox");
        System.out.println("  6. View logs");
        System.out.println("  7. Generate system report");
        System.out.println("  8. Save data");
        System.out.println("  9. Load data");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.print("Type (student/teacher/research/manager/admin/tech): ");
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
                System.out.print("ID or login of user to remove: ");
                String key = scanner.nextLine().trim();
                String userId = findByLogin(key).map(User::getId).orElse(key);
                admin.removeUser(userId);
                if (storage.removeUser(key)) {
                    System.out.println("User removed.");
                } else {
                    System.out.println("User not found in storage.");
                }
            }
            case "3" -> storage.getUsers().forEach(System.out::println);
            case "4" -> sendMessageMenu(admin);
            case "5" -> printMessages(admin);
            case "6" -> admin.viewLogFiles().forEach(System.out::println);
            case "7" -> System.out.println(admin.generateSystemReport());
            case "8" -> saveStorage();
            case "9" -> loadStorage();
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // TEACHER MENU
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
        System.out.println(" 12. View attendance rate");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                System.out.println("=== My Courses ===");
                teacher.getTaughtCourses().forEach(c -> System.out.println("  " + c.getTitle() + " (" + c.getCourseId() + ")"));
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
                    teacher.viewStudents(course).forEach(System.out::println);
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
                            journal, citations, pages, new Date(), doi);
                    teacher.publishPaper(paper);
                    storage.addResearchPaper(paper);
                    storage.addNews(NewsGenerator.createFromPaper(paper));
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
                        Request request = teacher.sendComplaint((Student) u, level, text);
                        storage.addRequest(request);
                        System.out.println("Complaint sent!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid urgency level.");
                    }
                }, () -> System.out.println("Student not found."));
            }
            case "8" -> sendMessageMenu(teacher);
            case "9" -> printMessages(teacher);
            case "10" -> printNews();
            case "11" -> markAttendanceMenu(teacher);
            case "12" -> attendanceRateMenu();
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // STUDENT MENU
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
                        c.getCourseId(), c.getTitle(), c.getCredits()));
            }
            case "2" -> {
                System.out.print("Course code: ");
                String courseCode = scanner.nextLine().trim();
                findCourseByCode(courseCode).ifPresentOrElse(course -> {
                    if (student.registerCourse(course)) {
                        System.out.println("Course registered!");
                    } else {
                        System.out.println("Course registration failed. You may already be registered or the course may not match your year, major, credits, or retake limit.");
                    }
                }, () -> System.out.println("Course not found."));
            }
            case "3" -> {
                System.out.println("=== My Courses ===");
                student.getEnrolledCourses().forEach(c -> System.out.println("  " + c.getTitle()));
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
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid rating. Use number from 1 to 5.");
                    }
                }, () -> System.out.println("Teacher not found."));
            }
            case "6" -> printNews();
            case "7" -> {
                System.out.print("Organization ID: ");
                String orgId = scanner.nextLine().trim();
                findOrganizationById(orgId).ifPresentOrElse(organization -> {
                    student.joinOrganization(organization);
                    System.out.println("Joined " + organization.getName() + ".");
                }, () -> System.out.println("Organization not found."));
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

    // MANAGER MENU
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
        System.out.println(" 12. Send official message");
        System.out.println(" 13. Research summary");
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
                        boolean approved = manager.approveRegistration((Student) u, course);
                        if (approved) System.out.println("Registration approved!");
                        else System.out.println("Registration was not approved.");

                    }, () -> System.out.println("Course not found."));
                }, () -> System.out.println("Student not found."));
            }
            case "4" -> {
                SortingStrategy<Student> strategy = new ComparatorSortingStrategy<>(UniversityComparators.BY_GPA_DESC);
                List<Student> students = SortingUtils.sort(manager.viewAllStudents(SortBy.ID), strategy);
                students.forEach(s -> System.out.printf("  %s - GPA: %.2f%n", s.getFullName(), s.getGpa()));
            }
            case "5" -> {
                SortingStrategy<Student> strategy = new ComparatorSortingStrategy<>(UniversityComparators.BY_NAME);
                List<Student> students = SortingUtils.sort(manager.viewAllStudents(SortBy.ID), strategy);
                students.forEach(s -> System.out.println("  " + s.getFullName()));
            }
            case "6" -> manager.viewAllTeachers().forEach(System.out::println);
            case "7" -> System.out.println(manager.generateStatisticalReport());
            case "8" -> {
                System.out.print("Title: ");
                String title = scanner.nextLine().trim();
                System.out.print("Content: ");
                String content = scanner.nextLine().trim();
                News item = new News(title, content, NewsType.NORMAL);
                storage.addNews(item);
                manager.manageNews(item, "add");
                System.out.println("News published!");
            }
            case "9" -> printNews();
            case "10" -> sendMessageMenu(manager);
            case "11" -> printMessages(manager);
            case "12" -> sendOfficialMessageMenu(manager);
            case "13" -> printResearchSummary();
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // TECH SUPPORT MENU
    private static void showTechSupportMenu(TechSupportSpecialist support) {
        System.out.println("\n+-- TECH SUPPORT MENU --+");
        System.out.println("  1. View all requests");
        System.out.println("  2. Update request status");
        System.out.println("  3. Send message");
        System.out.println("  4. View inbox");
        System.out.println("  0. Logout");
        System.out.print("Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> {
                syncSupportRequests(support);
                support.viewRequests().forEach(System.out::println);
            }
            case "2" -> {
                System.out.print("Request ID: ");
                String requestId = scanner.nextLine().trim();
                findRequestById(requestId).ifPresentOrElse(request -> {
                    System.out.println("Current status: " + request.getStatus());
                    System.out.print("New status (PENDING/ACCEPTED/REJECTED/DONE/VIEWED): ");
                    try {
                        RequestStatus status = RequestStatus.valueOf(scanner.nextLine().trim().toUpperCase());
                        commandInvoker.execute(new RequestStatusCommand(support, request, status));
                        System.out.println("Status updated!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status.");
                    }
                }, () -> System.out.println("Request not found."));
            }
            case "3" -> sendMessageMenu(support);
            case "4" -> printMessages(support);
            case "0" -> logout();
            default -> System.out.println("Invalid choice.");
        }
    }

    // HELPER METHODS

    private static Optional<User> findByLogin(String login) {
        return storage.getUsers().stream()
                .filter(u -> u.getEmail().equals(login))
                .findFirst();
    }

    private static Optional<Course> findCourseByCode(String code) {
        return storage.getCourses().stream()
                .filter(c -> c.getCourseId().equals(code))
                .findFirst();
    }

    private static Optional<Organization> findOrganizationById(String id) {
        return storage.getOrganizations().stream()
                .filter(o -> o.getOrgId().equals(id))
                .findFirst();
    }

    private static Optional<Request> findRequestById(String id) {
        return storage.getRequests().stream()
                .filter(r -> r.getRequestId().equals(id) || r.getRequestInfo().contains("#" + id + " "))
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
                    course.getLessons().stream()
                            .filter(l -> l.getType() == type)
                            .findFirst()
                            .ifPresent(lesson -> {
                                attendanceService.markAttendance((Student) u, lesson, status);
                                System.out.println("Attendance marked!");
                            });
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input.");
                }
            }, () -> System.out.println("Student not found."));
        }, () -> System.out.println("Course not found."));
    }

    private static void attendanceRateMenu() {
        System.out.print("Student login: ");
        findByLogin(scanner.nextLine().trim()).ifPresentOrElse(u -> {
            if (!(u instanceof Student)) {
                System.out.println("Not a student.");
                return;
            }
            double rate = attendanceService.calculateAttendanceRate((Student) u);
            System.out.printf("Attendance rate for %s: %.2f%%%n", u.getFullName(), rate * 100);
        }, () -> System.out.println("Student not found."));
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

    private static void sendOfficialMessageMenu(Employee sender) {
        System.out.print("Recipient login: ");
        findByLogin(scanner.nextLine().trim()).ifPresentOrElse(recipient -> {
            System.out.print("Subject: ");
            String subject = scanner.nextLine().trim();
            System.out.print("Text: ");
            String text = scanner.nextLine().trim();
            System.out.print("Room: ");
            String room = scanner.nextLine().trim();
            OfficialMessage message = new OfficialMessage(sender, recipient, subject, text, new Date(), room);
            System.out.println(message);
            if (recipient instanceof Employee) {
                sender.sendMessage((Employee) recipient, message.toString());
            }
        }, () -> System.out.println("User not found."));
    }

    private static void printNews() {
        if (getNews().isEmpty()) {
            System.out.println("No news yet.");
            return;
        }
        getNews().forEach(item -> System.out.println(item + "\n"));
    }

    private static void printResearchSummary() {
        ResearchAnalytics analytics = new ResearchAnalytics();
        List<interfaces.Researcher> researchers = storage.getUsers().stream()
                .filter(u -> u instanceof interfaces.Researcher)
                .map(u -> (interfaces.Researcher) u)
                .toList();
        System.out.println("=== All research papers by citations ===");
        analytics.printAllResearchPapers(researchers, UniversityComparators.PAPER_BY_CITATIONS_DESC);
        if (!researchers.isEmpty()) {
            interfaces.Researcher top = analytics.topCitedResearchers(researchers, 1).get(0);
            if (top instanceof User) {
                News item = NewsGenerator.createTopResearcherNews((User) top);
                storage.addNews(item);
                System.out.println("Top researcher news generated:");
                System.out.println(item);
            }
        }
    }

    private static void syncSupportRequests(TechSupportSpecialist support) {
        storage.getRequests().forEach(request -> support.updateRequestStatus(request, request.getStatus()));
    }

    private static void saveStorage() {
        try {
            storage.save();
            System.out.println("Data saved.");
        } catch (Exception e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private static void loadStorage() {
        try {
            storage = DataStorage.load();
            System.out.println("Data loaded.");
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
        }
    }

    private static User createUserByType(String type, String id, String name, String login, String password) {
        Date now = new Date();
        return switch (type.toLowerCase()) {
            case "student" -> new Student(id, name, login, password, "EN", "S-" + id, "SITE", 1, 0.0, 0, new ArrayList<>(), new ArrayList<>());
            case "teacher" -> new Teacher(id, name, login, password, "EN", 300000, now, "EMP-" + id, "T-" + id, TeacherPosition.LECTOR, new ArrayList<>());
            case "research" -> new ResearchEmployee(id, name, login, password, "EN", 350000, now, "RES-" + id);
            case "manager" -> new Manager(id, name, login, password, "EN", 400000, now, "MAN-" + id, ManagerType.DEPARTMENT_MANAGER, "SITE");
            case "admin" -> new Admin(id, name, login, password, "EN", 500000, now, "ADM-" + id);
            case "tech" -> new TechSupportSpecialist(id, name, login, password, "EN", 350000, now, "SUP-" + id);
            default -> null;
        };
    }

    private static void logout() {
        System.out.println("Goodbye, " + currentUser.getFullName() + "!");
        currentUser.logout();
        currentUser = null;
    }

    private static void printMessages(Employee employee) {
        List<Message> messages = employee.viewMessages();
        if (messages.isEmpty()) {
            System.out.println("Inbox is empty.");
            return;
        }
        messages.forEach(System.out::println);
    }

    // =========================================================
    // SEED DATA (using your existing classes)
    // =========================================================
    private static void seedData() {
        Date now = new Date();

        if (!storage.getUsers().isEmpty()) return;

        Admin admin = new Admin("a1", "Admin", "admin", "admin123", "EN", 500000, now, "ADM-1");
        Teacher teacher = new Teacher("t1", "Arman Myrzakanurov", "arman", "arman123", "EN", 600000, now, "EMP-T1", "T-1", TeacherPosition.PROFESSOR, new ArrayList<>());
        Student student = new Student("s1", "Kamshat", "kamshat", "kamshat123", "EN", "S-1", "SITE", 2, 4.0, 0, new ArrayList<>(), new ArrayList<>());
        Student student2 = new Student("s2", "Lev", "lev", "lev123", "EN", "S-2", "SITE", 2, 3.67, 0, new ArrayList<>(), new ArrayList<>());
        Manager manager = new Manager("m1", "Akzhainak", "akzhainak", "akzhainak123", "EN", 450000, now, "MAN-1", ManagerType.DEPARTMENT_MANAGER, "SITE");
        TechSupportSpecialist support = new TechSupportSpecialist("ts1", "Nurbolsyn", "nurbolsyn", "nurbolsyn123", "EN", 450000, now, "SUP-1");

        admin.addUser(admin);
        admin.addUser(teacher);
        admin.addUser(student);
        admin.addUser(student2);
        admin.addUser(manager);
        admin.addUser(support);
        storage.addUser(admin);
        storage.addUser(teacher);
        storage.addUser(student);
        storage.addUser(student2);
        storage.addUser(manager);
        storage.addUser(support);

        Course course = new Course("CSCI2106", "Object-Oriented Programming", 5, "SITE", 2, CourseType.MAJOR);
        storage.addCourse(course);

        manager.addStudent(student);
        manager.addStudent(student2);
        manager.addTeacher(teacher);
        manager.assignCourseToTeacher(course, teacher, LessonType.LECTURE);
        manager.assignCourseToTeacher(course, teacher, LessonType.PRACTICE);
        manager.approveRegistration(student, course);
        manager.approveRegistration(student2, course);
        course.addLesson(new Lesson("L-1", LessonType.LECTURE, new Date(126, 3, 28), "10:00", "424", course, teacher));
        course.addLesson(new Lesson("L-2", LessonType.PRACTICE, new Date(126, 3, 27), "10:00", "365", course, teacher));

        Organization organization = new Organization("ORG-1", "Dream Team Club");
        organization.addMember(student);
        organization.addMember(student2);
        organization.setHead(student);
        storage.addOrganization(organization);

        Request request = new Request("REQ-1", "Projector is not working", student);
        storage.addRequest(request);
        support.updateRequestStatus(request, request.getStatus());

        ResearchPaper teacherPaper = new ResearchPaper(
                "LMS Logs and Student Performance",
                List.of(teacher.getFullName()),
                "Dream Journal",
                8,
                12,
                now,
                "10.1000/oop-demo"
        );
        teacher.publishPaper(teacherPaper);
        storage.addResearchPaper(teacherPaper);
        storage.addNews(NewsGenerator.createFromPaper(teacherPaper));
        storage.addNews(NewsGenerator.createTopResearcherNews(teacher));

        System.out.println("=== Test data loaded ===");
        System.out.println("Logins: admin/admin123 | arman/arman123 | kamshat/kamshat123 | lev/lev123 | akzhainak/akzhainak123 | nurbolsyn/nurbolsyn123");
    }
}

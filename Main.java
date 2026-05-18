import comparators.UniversityComparators;
import enums.CourseType;
import enums.Degree;
import enums.Format;
import enums.Language;
import enums.LessonType;
import enums.ManagerType;
import enums.NewsType;
import enums.OrganizationRole;
import enums.RequestStatus;
import enums.SortBy;
import enums.TeacherPosition;
import enums.UrgencyLevel;
import exceptions.NotResearcherException;
import exceptions.SupervisorException;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Printable;
import interfaces.Ratable;
import interfaces.Researcher;
import models.Admin;
import models.AuthenticationService;
import models.Comment;
import models.Course;
import models.Employee;
import models.GraduateStudent;
import models.Journal;
import models.Lesson;
import models.Manager;
import models.Mark;
import models.MasterStudent;
import models.Message;
import models.MessageService;
import models.News;
import models.NewsGenerator;
import models.OfficialMessage;
import models.Organization;
import models.PhDStudent;
import models.Report;
import models.Request;
import models.ResearchAnalytics;
import models.ResearchPaper;
import models.ResearchProject;
import models.RoomBooking;
import models.Student;
import models.Subscription;
import models.Teacher;
import models.TeacherRating;
import models.TechSupportSpecialist;
import models.Transcript;
import models.User;
import models.UserLog;
import patterns.DataStorage;
import patterns.SortingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Date now = new Date();

        User user = new User("u1", "Simple User", "user@uni.kz", "123", Language.EN.name());
        Employee employee = new Employee("e1", "Base Employee", "employee@uni.kz", "123", Language.EN.name(), 250000, now, "EMP-1");
        Admin admin = new Admin("a1", "System Admin", "admin@uni.kz", "123", Language.EN.name(), 400000, now, "ADM-1");
        Manager manager = new Manager("m1", "Academic Manager", "manager@uni.kz", "123", Language.EN.name(), 450000, now, "MAN-1", ManagerType.DEPARTMENT_MANAGER, "SITE");
        TechSupportSpecialist support = new TechSupportSpecialist("ts1", "Tech Support", "support@uni.kz", "123", Language.EN.name(), 300000, now, "SUP-1");

        Course course = new Course("CS101", "Object-Oriented Programming", 5, "SITE", 2, CourseType.MAJOR);
        Course elective = new Course("HUM101", "Communication", 3, "HUM", 2, CourseType.MINOR);
        Course freeCourse = new Course("FREE101", "Free Elective", 2, "ANY", 0, CourseType.FREE_ELECTIVE);

        Teacher teacher = new Teacher("t1", "Professor Ada", "ada@uni.kz", "123", Language.EN.name(), 600000, now, "EMP-T1", "T-1", TeacherPosition.PROFESSOR, new ArrayList<>());
        Student student = new Student("s1", "Student Bob", "bob@uni.kz", "123", Language.EN.name(), "S-1", "SITE", 2, 3.6, 0, new ArrayList<>(), new ArrayList<>());
        Student student2 = new Student("s2", "Student Alice", "alice@uni.kz", "123", Language.EN.name(), "S-2", "SITE", 2, 3.95, 0, new ArrayList<>(), new ArrayList<>());
        Student student3 = new Student("s3", "Student Dias", "dias@uni.kz", "123", Language.EN.name(), "S-3", "SITE", 2, 2.75, 0, new ArrayList<>(), new ArrayList<>());
        Student student4 = new Student("s4", "Student Mira", "mira@uni.kz", "123", Language.EN.name(), "S-4", "SITE", 2, 3.2, 0, new ArrayList<>(), new ArrayList<>());
        Student student5 = new Student("s5", "Student Nurlan", "nurlan@uni.kz", "123", Language.EN.name(), "S-5", "SITE", 2, 1.9, 0, new ArrayList<>(), new ArrayList<>());

        manager.addStudent(student);
        manager.addStudent(student2);
        manager.addStudent(student3);
        manager.addStudent(student4);
        manager.addStudent(student5);
        manager.addTeacher(teacher);
        manager.addCourseForRegistration(course, 2, "SITE");
        manager.assignCourseToTeacher(course, teacher, LessonType.LECTURE);
        manager.assignCourseToTeacher(course, teacher, LessonType.PRACTICE);
        manager.approveRegistration(student, course);
        manager.approveRegistration(student2, course);
        manager.approveRegistration(student3, course);
        manager.approveRegistration(student4, course);
        manager.approveRegistration(student5, course);

        Lesson lesson = new Lesson("L-1", LessonType.LECTURE, now, "09:00", "305", course, teacher);
        course.addLesson(lesson);

        Mark mark = new Mark(30, 30, 35);
        teacher.putMark(student, course, mark);
        Transcript transcript = student.viewTranscript();
        student.rateTeacher(teacher, 5);

        Organization organization = new Organization("ORG-1", "Dream Team Club");
        student.joinOrganization(organization);
        organization.electHead(student);
        organization.setHead(student);

        Request request = new Request("REQ-1", "Projector is not working", student);
        support.acceptRequest(request);
        support.markRequestDone(request);

        Message message = new Message(user, student, "Welcome to the system");
        MessageService messageService = new MessageService();
        messageService.sendMessage(user, student, message.getText());
        OfficialMessage officialMessage = new OfficialMessage();

        News news = new News("OOP Week", "Midterm week starts soon", NewsType.NORMAL);
        Comment comment = new Comment("Good luck!", student);
        news.addComment(comment);
        manager.manageNews(news, "pin");

        ResearchPaper paper1 = new ResearchPaper("Clean OOP Design", Arrays.asList("Professor Ada", "Student Bob"), "Dream Journal", 12, 8, now, "10.1000/oop1");
        ResearchPaper paper2 = new ResearchPaper("University Systems", Arrays.asList("Professor Ada"), "Dream Journal", 4, 6, now, "10.1000/oop2");
        ResearchPaper paper3 = new ResearchPaper("Research Analytics", Arrays.asList("Student Bob"), "Dream Journal", 2, 5, now, "10.1000/oop3");
        List<ResearchPaper> papers = Arrays.asList(paper1, paper2, paper3);

        Researcher supervisor = new DemoResearcher(papers);
        try {
            GraduateStudent graduateStudent = new GraduateStudent("g1", "Graduate Cora", "cora@uni.kz", "123", Language.EN.name(), "G-1", "SITE", 1, 3.8, 0, new ArrayList<>(), new ArrayList<>(), "OOP thesis", supervisor);
            MasterStudent masterStudent = new MasterStudent("ms1", "Master Dana", "dana@uni.kz", "123", Language.EN.name(), "M-1", "SITE", 1, 3.9, 0, new ArrayList<>(), new ArrayList<>(), "Master thesis", supervisor, 24);
            PhDStudent phdStudent = new PhDStudent("phd1", "PhD Emir", "emir@uni.kz", "123", Language.EN.name(), "P-1", "SITE", 2, 4.0, 0, new ArrayList<>(), new ArrayList<>(), "PhD thesis", supervisor, "AI in education", papers);

            ResearchProject project = new ResearchProject("Digital University", now, null);
            project.addPaper(paper1);
            graduateStudent.publishPaper(paper1);
            graduateStudent.joinProject(project);
            masterStudent.publishPaper(paper2);
            phdStudent.publishPaper(paper3);
            project.addParticipant(supervisor);

            try {
                project.addParticipant((Object) user);
            } catch (NotResearcherException e) {
                System.out.println("Expected research exception: " + e.getMessage());
            }

            ResearchAnalytics analytics = new ResearchAnalytics();
            List<Researcher> topResearchers = analytics.topCitedResearchers(Arrays.asList(supervisor, graduateStudent, masterStudent, phdStudent), 3);
            System.out.println("Top researchers: " + topResearchers.size());
            System.out.println("Papers this year: " + analytics.papersByYear(papers, now.getYear() + 1900).size());
            System.out.println("PhD topic: " + phdStudent.getDissertationTopic());
            System.out.println("Master credits: " + masterStudent.getCourseWorkCredits());
        } catch (SupervisorException e) {
            System.out.println("Supervisor error: " + e.getMessage());
        }

        Journal journal = new Journal("Dream Journal");
        journal.subscribe(student);
        journal.publishPaper(paper1);
        Subscription subscription = new Subscription(student, journal);
        News researchNews = NewsGenerator.createFromPaper(paper1);
        News topResearcherNews = NewsGenerator.createTopResearcherNews(student);

        Report report = manager.generateStatisticalReport();
        UserLog log = new UserLog(admin.getId(), "RUN_DEMO");
        AuthenticationService auth = new AuthenticationService();
        boolean loggedIn = auth.login(user, "user@uni.kz", "123");
        auth.logout();

        admin.addUser(user);
        admin.addUser(student);
        admin.updateUser(user);

        DataStorage storage = DataStorage.getInstance();
        storage.addUser(user);
        storage.addUser(student);
        storage.addCourse(course);
        storage.addResearchPaper(paper1);

        List<Student> sortedByManager = manager.viewAllStudents(SortBy.GPA);
        List<Student> sortedByUtils = SortingUtils.sortStudents(sortedByManager, UniversityComparators.BY_GPA_DESC);
        List<Student> sortedByName = manager.viewAllStudents(SortBy.NAME);
        List<Student> sortedById = manager.viewAllStudents(SortBy.ID);

        RoomBooking roomBooking = new RoomBooking("305", "2026-05-18");
        Observable observable = new DemoObservable();
        Observer observer = messageText -> System.out.println("Observer received: " + messageText);
        observable.addObserver(observer);
        observable.notifyObservers("Schedule updated");
        observable.removeObserver(observer);

        Printable printable = () -> System.out.println(course);
        Ratable ratable = new Ratable() {
            private double total;
            private int count;

            @Override
            public void addRating(double rating) {
                total += rating;
                count++;
                System.out.println("Manual rating: " + rating);
            }

            @Override
            public double getAverageRating() {
                return count == 0 ? 0 : total / count;
            }
        };
        printable.printInfo();
        ratable.addRating(4.5);

        System.out.println("Enums: " + Degree.MASTER + ", " + Degree.PHD + ", " + OrganizationRole.HEAD + ", " + OrganizationRole.MEMBER + ", " + RequestStatus.VIEWED + ", " + UrgencyLevel.HIGH);
        System.out.println("Login ok: " + loggedIn);
        System.out.println("Employee salary: " + employee.getSalary());
        System.out.println("Message: " + message.getText());
        System.out.println("Comment: " + comment.getText());
        System.out.println("Lesson: " + lesson.getLessonInfo());
        System.out.println("Mark: " + mark);
        System.out.println("Transcript GPA: " + transcript.getGpa());
        System.out.println("Teacher rating: " + TeacherRating.getRating(teacher));
        System.out.println("Manual rating average: " + ratable.getAverageRating());
        System.out.println("Request: " + request.getRequestInfo());
        System.out.println("Report: " + report);
        System.out.println("Log: " + log);
        System.out.println("BibTeX: " + paper1.getCitation(Format.BIBTEX));
        System.out.println("Plain citation: " + paper1.getCitation(Format.PLAIN_TEXT));
        System.out.println("Subscription user: " + subscription.getUser().getFullName());
        System.out.println("News: " + news.getTitle() + ", " + researchNews.getTitle() + ", " + topResearcherNews.getTitle());
        System.out.println("Sorted students: " + sortedByUtils.size() + ", by name: " + sortedByName.size() + ", by id: " + sortedById.size());
        System.out.println("Storage users: " + storage.getUsers().size() + ", courses: " + storage.getCourses().size() + ", papers: " + storage.getResearchPapers().size());
        System.out.println("Extra objects: " + officialMessage.getClass().getSimpleName() + ", " + roomBooking.getClass().getSimpleName() + ", " + elective.getTitle() + ", " + freeCourse.getTitle());
        System.out.println("Admin report: " + admin.generateSystemReport());
    }

    private static class DemoResearcher implements Researcher {
        private final List<ResearchPaper> papers;
        private final List<ResearchProject> projects = new ArrayList<>();

        private DemoResearcher(List<ResearchPaper> papers) {
            this.papers = new ArrayList<>(papers);
        }

        @Override
        public int calculateHIndex() {
            return 3;
        }

        @Override
        public void printPapers(java.util.Comparator<ResearchPaper> comparator) {
            List<ResearchPaper> copy = new ArrayList<>(papers);
            if (comparator != null) {
                copy.sort(comparator);
            }
            for (ResearchPaper paper : copy) {
                System.out.println(paper.getCitation(Format.PLAIN_TEXT));
            }
        }

        @Override
        public List<ResearchProject> getResearchProjects() {
            return new ArrayList<>(projects);
        }

        @Override
        public List<ResearchPaper> getResearchPapers() {
            return new ArrayList<>(papers);
        }

        @Override
        public void publishPaper(ResearchPaper paper) {
            if (paper != null) {
                papers.add(paper);
            }
        }

        @Override
        public void joinProject(ResearchProject project) {
            if (project != null && !projects.contains(project)) {
                projects.add(project);
            }
        }
    }

    private static class DemoObservable implements Observable {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void addObserver(Observer observer) {
            if (observer != null && !observers.contains(observer)) {
                observers.add(observer);
            }
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers(String message) {
            for (Observer observer : observers) {
                observer.update(message);
            }
        }
    }
}

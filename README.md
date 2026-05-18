# OOP Dream Team

Research-oriented university system for the OOP final project.

## Implemented requirements

### Core users

- `User` stores account data, language, login state, password updates, language switching, `equals`, `hashCode`, and `toString`.
- `Employee` extends `User` and supports working employee-to-employee messages.
- `Teacher` manages courses, views students, puts marks, sends complaints with urgency levels, and implements `Researcher`.
- `Student` registers and drops courses, views marks/transcript, rates teachers, and joins organizations.
- `Student` can view the teacher of a specific course lesson type.
- `GraduateStudent`, `MasterStudent`, and `PhDStudent` model graduate students and implement research behavior.
- `ResearchEmployee` models an employee who is neither teacher nor student but is still a researcher.
- `Admin` manages users and stores user action logs.
- `Manager` assigns courses to teachers, approves registration, manages news, and generates academic reports.
- `TechSupportSpecialist` views requests, marks pending requests as `VIEWED`, and can accept/reject/finish them.

### Academic module

- `Course` supports major, minor, and free elective course types, lecture/practice teachers, lesson lists, 75 seats, and registration constraints.
- `Lesson` supports lecture/practice lesson types.
- `Mark` consists of first attestation, second attestation, and final exam.
- `Transcript` calculates GPA from marks and course credits.
- `TeacherRating` stores student ratings for teachers.
- `AttendanceRecord` and `AttendanceService` implement the extra Attendance task.

### Research module

- `Researcher` is an interface used by teachers, graduate students, and research employees.
- `ResearchPaper` stores title, authors, journal, citations, pages, publication date, and DOI.
- `ResearchPaper.getCitation(Format)` supports `PLAIN_TEXT` and `BIBTEX`.
- `ResearchProject` has topic, papers, participants, and throws `NotResearcherException` when a non-researcher joins.
- `GraduateStudent` throws `SupervisorException` if the supervisor h-index is below 3.
- `ResearchAnalytics` supports top researchers, papers by year, paper sorting, and top cited researcher of a year.
- `ResearchAnalytics` supports collecting/printing papers of all researchers sorted by comparator.
- `UniversityComparators` supports GPA, teacher, and research paper comparators by citations/date/pages.
- `AbstractReportGenerator` and `AcademicPerformanceReportGenerator` provide an abstract report-generation base class and a concrete report implementation.

### News, journals, and messages

- `News` supports comments and research news pinning.
- `NewsGenerator` creates news from papers and top researchers.
- `Journal` supports user subscriptions and Observer notifications when a new paper is published.
- `Message` and `OfficialMessage` cover ordinary and official university messages.

### Storage and patterns

- `DataStorage` is a Singleton and supports serialization save/load.
- `Journal` + `Observable`/`Observer` implement Observer.
- `NewsFactory`, `PaperNewsFactory`, and `TopResearcherNewsFactory` implement Factory Method for research news.
- `SortingStrategy` + `ComparatorSortingStrategy` implement Strategy for reusable sorting.
- `Command`, `RequestStatusCommand`, and `CommandInvoker` implement Command for tech-support request actions.

Implemented design patterns count: 5.

## Enumerations

The project uses enums for attendance status, course type, citation format, language, lesson type, manager type, news type, request status, teacher position, sorting, and urgency.

## Demo coverage

`Main` includes console flows for:

- authentication;
- language switching;
- course registration;
- lecture and practice lessons;
- attendance records;
- teacher mark putting and teacher rating;
- student teacher-info lookup;
- student organization membership/head;
- support request lifecycle including `VIEWED`;
- ordinary and official messages;
- research-generated news and top researcher news;
- research papers, h-index, and paper sorting;
- professor teacher as a researcher, with support for creating research employees;
- data storage serialization save/load;
- manager sorting by GPA/name.

## Remaining non-code deliverables

For final submission, add the PDF report, UML class diagram, use case diagram, and presentation PDF required by `OOP_Final_Project.pdf`.

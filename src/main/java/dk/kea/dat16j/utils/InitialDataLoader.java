package dk.kea.dat16j.utils;

import dk.kea.dat16j.models.*;
import dk.kea.dat16j.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Created by Chris on 19-Nov-17.
 */
@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {


    private boolean alreadySetup = false;

    private final AccountRepository accountRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudyProgrammeRepository studyProgrammeRepository;
    private final StudentRepository studentRepository;
    private final AdministratorWorkerRepository administratorWorkerRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataLoader(AccountRepository accountRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, StudyProgrammeRepository studyProgrammeRepository, StudentRepository studentRepository, AdministratorWorkerRepository administratorWorkerRepository, AccountRoleRepository accountRoleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studyProgrammeRepository = studyProgrammeRepository;
        this.studentRepository = studentRepository;
        this.administratorWorkerRepository = administratorWorkerRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        alreadySetup = true;



        AccountRole studRole = createRoleIfNotFound(AccountRoles.STUDENT.getRole());
        AccountRole teacherRole = createRoleIfNotFound(AccountRoles.TEACHER.getRole());
        AccountRole adminRole = createRoleIfNotFound(AccountRoles.ADMINISTRATOR.getRole());

        Account account = new Account();
        account.setUsername("sand@kea.dk");
        account.setPassword(passwordEncoder.encode("1234"));
        account.setAccountRole(teacherRole);
        account.setEnabled(true);
        createAccountIfNotFound(account);

        Account account2 = new Account();
        account2.setUsername("coag@kea.dk");
        account2.setPassword(passwordEncoder.encode("1234"));
        account2.setAccountRole(teacherRole);
        account2.setEnabled(true);
        createAccountIfNotFound(account2);

        Teacher andreaa = new Teacher();
        andreaa.setFirstName("Andreea");
        andreaa.setLastName("Coradini");
        createTeacherIfNotFound(andreaa);

        Teacher faisal = new Teacher();
        faisal.setFirstName("Faisal");
        faisal.setLastName("Jarkass");
        createTeacherIfNotFound(faisal);

        Teacher alex = new Teacher();
        alex.setFirstName("Constantin Alexandru");
        alex.setLastName("Gheorghiasa");
        alex.setAccount(account2);
        createTeacherIfNotFound(alex);

        Teacher santiago = new Teacher();
        santiago.setFirstName("Santiago");
        santiago.setLastName("Donoso");
        santiago.setAccount(account);
        createTeacherIfNotFound(santiago);

        Account student1Account = new Account();
        student1Account.setAccountRole(studRole);
        student1Account.setUsername("stud@stud.kea.dk");
        student1Account.setPassword(passwordEncoder.encode("1234"));
        student1Account.setEnabled(true);
        createAccountIfNotFound(student1Account);

        Student student = new Student();
        student.setFirstName("DummyFirstName");
        student.setLastName("DummyLastName");
        student.setAccount(student1Account);
        createStudentIfNotFound(student);

        Account student2Account = new Account();
        student2Account.setAccountRole(studRole);
        student2Account.setUsername("another@stud.kea.dk");
        student2Account.setPassword(passwordEncoder.encode("1234"));
        student2Account.setEnabled(true);
        createAccountIfNotFound(student2Account);

        Student student2 = new Student();
        student2.setFirstName("AnotherDummyFirstName");
        student2.setLastName("AnotherDummyLastName");
        student2.setAccount(student2Account);
        createStudentIfNotFound(student2);

        Account adminAccount = new Account();
        adminAccount.setUsername("admin@kea.dk");
        adminAccount.setPassword(passwordEncoder.encode("1234"));
        adminAccount.setAccountRole(adminRole);
        adminAccount.setEnabled(true);
        createAccountIfNotFound(adminAccount);

        AdministrationWorker administrationWorker = new AdministrationWorker();
        administrationWorker.setFirstName("AdminFirstName");
        administrationWorker.setLastName("AdminLastName");
        administrationWorker.setAccount(adminAccount);
        createAdministratorIfNotFound(administrationWorker);

        Course course = new Course();
        course.setLearningContent("NodeJS, Flexbox, Grid, CSS, MongoDB, AJAX, Websockets, JSON objects, Setting up a server in Amazon Web Servers and locally. Use of the terminal and FTP. Also, the setup and use of HTTPS.");
        course.setCourseLanguage("English");
        course.setDanishName("Full Stack NodeJs");
        course.setEcts(10);
        course.setEnglishName("Full Stack NodeJs");
        course.setExamForm("Internal oral exam based on hand in product. Graded based on the 7-scale.");
        course.setLearningActivities("Individual work and exam. Communication takes place via our Ryver channel WD-2018-F-NODEJS");
        course.setLearningContent("NodeJS, Flexbox, Grid, CSS, MongoDB, AJAX, Websockets, JSON objects, Setting up a server in Amazon Web Servers and locally. Use of the terminal and FTP. Also, the setup and use of HTTPS.");
        course.setMandatory(false);
        course.setLearningOutcome("Students will be able to code a full stack web based application, set-up a NODEJS server in the cloud and decide the best possible use of MongoDB");
        course.setPrerequisites("Students must know HTML, CSS, JS, PHP and MySQL.");
        course.setMaximumNumberOfStudent(50);
        course.setMinimumNumberOfStudent(15);
        course.setExpectedNumberOfStudent(35);
        course.setClassCode("WD-2018-F-NODEJS");
        course.setSemester(2);
        course.setTeachers(Arrays.asList(alex));
        createCourseIfNotFound(course);

        StudyProgramme studyProgramme1 = new StudyProgramme();
        studyProgramme1.setName("Computer Science");
        StudyProgramme studyProgramme2 = new StudyProgramme();
        studyProgramme2.setName("Web Development");
        studyProgramme2.setCourses(Arrays.asList(course));
        StudyProgramme studyProgramme3 = new StudyProgramme();
        studyProgramme3.setName("Software Development");
        StudyProgramme studyProgramme4 = new StudyProgramme();
        studyProgramme4.setName("IT-Security");

        createStudyProgrammeIfNotFound(studyProgramme1);
        createStudyProgrammeIfNotFound(studyProgramme2);
        createStudyProgrammeIfNotFound(studyProgramme3);
        createStudyProgrammeIfNotFound(studyProgramme4);
    }

    @Transactional
    protected Teacher createTeacherIfNotFound(Teacher teacher) {
        for (Teacher t : teacherRepository.findAll()) {
            if (t.getFirstName().equals(teacher.getFirstName()) && t.getLastName().equals(teacher.getLastName())) {
                teacher.setId(t.getId());
                return t;
            }
        }

        return teacherRepository.save(teacher);
    }

    @Transactional
    protected Student createStudentIfNotFound(Student student) {
        for (Student stud : studentRepository.findAll()) {
            if (stud.getFirstName().equals(student.getFirstName()) && stud.getLastName().equals(student.getLastName())) {
                student.setId(stud.getId());
                return stud;
            }
        }

        return studentRepository.save(student);
    }

    @Transactional
    protected AdministrationWorker createAdministratorIfNotFound(AdministrationWorker administrationWorker) {
        for (AdministrationWorker worker : administratorWorkerRepository.findAll()) {
            if (worker.getFirstName().equals(administrationWorker.getFirstName()) && worker.getLastName().equals(administrationWorker.getLastName())) {
                administrationWorker.setId(worker.getId());
                return worker;
            }
        }

        return administratorWorkerRepository.save(administrationWorker);
    }

    @Transactional
    protected AccountRole createRoleIfNotFound(String name) {
        AccountRole role = accountRoleRepository.findByRoleName(name);
        if (role == null) {
            role = new AccountRole(name);
            accountRoleRepository.save(role);
        }
        return role;
    }

    @Transactional
    protected Account createAccountIfNotFound(Account account) {
        for (Account acc : accountRepository.findAll()) {
            if (acc.getUsername().equals(account.getUsername()) && acc.getAccountRole().getRoleName().equals(account.getAccountRole().getRoleName())) {
                account.setId(acc.getId());
                return acc;
            }
        }

        return accountRepository.save(account);
    }

    @Transactional
    protected Course createCourseIfNotFound(Course course) {
        for (Course c : courseRepository.findAll()) {
            if (c.getDanishName().equals(course.getDanishName()) && c.getEnglishName().equals(course.getEnglishName()) &&
                    c.getEcts() == course.getEcts() && c.getCourseLanguage().equals(course.getCourseLanguage()) &&
                    c.getClassCode().equals(course.getClassCode())) {
                course.setId(c.getId());
                return c;
            }
        }

        return courseRepository.save(course);
    }

    @Transactional
    protected StudyProgramme createStudyProgrammeIfNotFound(StudyProgramme studyProgramme) {
        for (StudyProgramme programme : studyProgrammeRepository.findAll()) {
            if (programme.getName().equals(studyProgramme.getName())) {
                studyProgramme.setId(programme.getId());
                return programme;
            }
        }

        return studyProgrammeRepository.save(studyProgramme);
    }

}

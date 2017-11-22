package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.StudyProgramme;
import dk.kea.dat16j.models.Teacher;
import dk.kea.dat16j.repositories.CourseRepository;
import dk.kea.dat16j.repositories.StudyProgrammeRepository;
import dk.kea.dat16j.repositories.TeacherRepository;
import dk.kea.dat16j.utils.CollectionsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Chris on 14-Nov-17.
 */
@Controller
@RequestMapping(path = "/teacher/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudyProgrammeRepository studyProgrammeRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository, StudyProgrammeRepository studyProgrammeRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studyProgrammeRepository = studyProgrammeRepository;
    }

    @GetMapping(path = "/all")
    public ModelAndView getAllCourses(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Teacher teacher = teacherRepository.findByAccount_Username(username);
        if (teacher == null) {
            throw new NullPointerException();
        }
        ModelAndView mv = new ModelAndView("courses-all");
        mv.getModel().put("coursesList", courseRepository.findAllByTeachers(teacher));

        return mv;
    }

    @GetMapping(path = "/create")
    public ModelAndView getCreateCoursePage() {
        ModelAndView mv = new ModelAndView("create-course");
        mv.getModel().put("studyProgrammes", studyProgrammeRepository.findAll());
        mv.getModel().put("teachersList", teacherRepository.findAll());
        return mv;
    }

    @PostMapping(path = "/save")
    public ModelAndView submitCreateCourse(
            @RequestParam
                    String danishName,
            @RequestParam
                    String englishName,
            @RequestParam(name = "studyProgrammes")
                    String[] studyProgrammes,
            @RequestParam
                    String mandatory,
            @RequestParam
                    int ects,
            @RequestParam
                    String courseLanguage,
            @RequestParam
                    int minStudents,
            @RequestParam
                    int expectedStudents,
            @RequestParam
                    int maxStudents,
            @RequestParam
                    String prerequisites,
            @RequestParam
                    String learningOutcome,
            @RequestParam
                    String learningContent,
            @RequestParam
                    String learningActivities,
            @RequestParam
                    String examForm,
            @RequestParam(name = "teachers")
                    Long[] teachers
    ) {
        if (expectedStudents < minStudents || expectedStudents > maxStudents) {
            // TODO: 15-Nov-17 Add this check at frontend as well
        }

        Course course = new Course();
        course.setDanishName(danishName);
        course.setEnglishName(englishName);
        course.setMandatory(mandatory.equalsIgnoreCase("mandatory"));
        course.setEcts(ects);
        course.setCourseLanguage(courseLanguage);
        course.setMinimumNumberOfStudent(minStudents);
        course.setMaximumNumberOfStudent(maxStudents);
        course.setExpectedNumberOfStudent(expectedStudents);
        course.setPrerequisites(prerequisites);
        course.setLearningOutcome(learningOutcome);
        course.setLearningContent(learningContent);
        course.setLearningActivities(learningActivities);
        course.setExamForm(examForm);
        course.setTeachers(CollectionsUtility.makeCollection(teacherRepository.findAll(Arrays.asList(teachers))));

        courseRepository.save(course);

        Collection<StudyProgramme> studyProgrammesObj = studyProgrammeRepository.findAllByNameIn(Arrays.asList(studyProgrammes)); // study programmes will not be be created here
        for (StudyProgramme programme : studyProgrammesObj) {
            programme.getCourses().add(course); // TODO: 17-Nov-17 Check if the study programme already has the course attached (when editing a course) 
            studyProgrammeRepository.save(studyProgrammesObj);
        }
        return new ModelAndView(new RedirectView("/teacher/courses/all"));
    }

    @GetMapping(path = "/{id}/edit") // TODO: 16-Nov-17 Change to a POST, to not show data
    public ModelAndView getCourseEditPage(@PathVariable(name = "id") long id,
                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        final Teacher teacher = teacherRepository.findByAccount_Username(username);

        Course course = courseRepository.findOne(id);
        if (course.getTeachers().stream().noneMatch(t -> t.getId() == teacher.getId())) {
            // Only teachers that created that course or a teaching it can edit the course
            throw new RuntimeException("The current teacher is not teaching the requested course");
        }
        // TODO: 16-Nov-17 Check for null, or open page 404

        ModelAndView mv = new ModelAndView("edit-course");
        mv.getModel().put("course", course);
        mv.getModel().put("teachersList", teacherRepository.findAll());
        mv.getModel().put("studyProgrammes", studyProgrammeRepository.findAll());

        return mv;
    }

    // FIXME: 17-Nov-17 Bad approach, try to reuse the submitCreateCourse method
    @PostMapping(path = "/{id}/submit-edit")
    public ModelAndView submitEditCourse(
            @PathVariable(name = "id")
                    long id,
            @RequestParam
                    String danishName,
            @RequestParam
                    String englishName,
            @RequestParam(name = "studyProgrammes") // TODO: 19-Nov-17 think about required = false: force user at front end to select smth or have course that is yet not part of study programmes
                    String[] studyProgrammes,
            @RequestParam
                    String mandatory,
            @RequestParam
                    int ects,
            @RequestParam
                    String courseLanguage,
            @RequestParam
                    int minStudents,
            @RequestParam
                    int expectedStudents,
            @RequestParam
                    int maxStudents,
            @RequestParam
                    String prerequisites,
            @RequestParam
                    String learningOutcome,
            @RequestParam
                    String learningContent,
            @RequestParam
                    String learningActivities,
            @RequestParam
                    String examForm,
            @RequestParam(name = "teachers") // TODO: 19-Nov-17 think about required = false: force user at front end to select smth or have course that has not teachers
                    Long[] teachers
    ) {
        if (expectedStudents < minStudents || expectedStudents > maxStudents) {
            // TODO: 15-Nov-17 Add this check at frontend as well
        }

        // FIXME: 17-Nov-17 Change the search to use something more unique, perhaps the Class Code? Speak with Alex about this
        Course course = courseRepository.findOne(id);
        if (course == null) {
            // People may change the URL directly, that means they might try an non-existent id
            throw new NullPointerException(String.format("Course for id %d not found.", id));
//            throw new ResourceNotFoundException();
        }
        course.setDanishName(danishName);
        course.setEnglishName(englishName);
        course.setMandatory(mandatory.equalsIgnoreCase("mandatory"));
        course.setEcts(ects);
        course.setCourseLanguage(courseLanguage);
        course.setMinimumNumberOfStudent(minStudents);
        course.setMaximumNumberOfStudent(maxStudents);
        course.setExpectedNumberOfStudent(expectedStudents);
        course.setPrerequisites(prerequisites);
        course.setLearningOutcome(learningOutcome);
        course.setLearningContent(learningContent);
        course.setLearningActivities(learningActivities);
        course.setExamForm(examForm);
        course.setTeachers(CollectionsUtility.makeCollection(teacherRepository.findAll(Arrays.asList(teachers))));

        courseRepository.save(course);

        final List<String> studyProgrammesNames = Arrays.asList(studyProgrammes);
        // TODO: 19-Nov-17 Make the StudyProgramme - Course mapping bidirectional? This will simplify the task of removing the course from study programmes
        Collection<StudyProgramme> studyProgrammesObj = studyProgrammeRepository.findAllByNameIn(studyProgrammesNames); // study programmes will not be be created here
        for (StudyProgramme programme : studyProgrammesObj) {

            // Before adding the course to the study programme, check if it doesn't already contain it
            // because it could happen that a course appears twice in the list
            // TODO: 17-Nov-17 Write a unit test to test this
            if (programme.getCourses()
                    .stream()
                    .noneMatch(c -> c.getId() == course.getId())) {

                programme.getCourses().add(course);
                studyProgrammeRepository.save(studyProgrammesObj);
            }
        }

        // TODO: 17-Nov-17 Write a unit test to test this
        // This will contain all the study programmes that have been not selected on the website by the user
        Collection<StudyProgramme> notSelectedStudyProgrammes = studyProgrammeRepository.findAllByNameNotIn(studyProgrammesNames);
        notSelectedStudyProgrammes.forEach(studyProgramme -> studyProgramme.removeCourseIfContains(course.getId()));
        studyProgrammeRepository.save(notSelectedStudyProgrammes);

        return new ModelAndView(new RedirectView("/teacher/courses/all"));
    }
}

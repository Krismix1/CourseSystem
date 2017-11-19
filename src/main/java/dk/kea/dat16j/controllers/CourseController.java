package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.StudyProgramme;
import dk.kea.dat16j.repositories.CourseRepository;
import dk.kea.dat16j.repositories.StudyProgrammeRepository;
import dk.kea.dat16j.repositories.TeacherRepository;
import dk.kea.dat16j.utils.CollectionsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Chris on 14-Nov-17.
 */
@Controller
@RequestMapping(path = "/courses")
public class CourseController {

    public static final Logger LOG = LoggerFactory.getLogger(CourseController.class);


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
    public ModelAndView getAllCourses() {
        ModelAndView mv = new ModelAndView("courses-all");
        mv.getModel().put("coursesList", courseRepository.findAll());

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
        return new ModelAndView(new RedirectView("/courses/all"));
    }

    @GetMapping(path = "/{id}/edit") // TODO: 16-Nov-17 Change to a POST, to not show data
    public ModelAndView getCourseEditPage(@PathVariable(name = "id") long id) {
        Course course = courseRepository.findOne(id);
        // TODO: 16-Nov-17 Check for null, or open page 404

        ModelAndView mv = new ModelAndView("edit-course");
        mv.getModel().put("course", course);
        mv.getModel().put("teachersList", teacherRepository.findAll());
        mv.getModel().put("studyProgrammes", studyProgrammeRepository.findAll());

        return mv;
    }
}

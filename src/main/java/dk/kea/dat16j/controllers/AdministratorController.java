package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.repositories.CourseRepository;
import dk.kea.dat16j.repositories.CourseRequestRepository;
import dk.kea.dat16j.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Chris on 20-Nov-17.
 */
@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    private final CourseRepository courseRepository;
    private final CourseRequestRepository courseRequestRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AdministratorController(CourseRepository courseRepository, CourseRequestRepository courseRequestRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.courseRequestRepository = courseRequestRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/course-selection")
    public ModelAndView getCourseSelection() {
        ModelAndView mv = new ModelAndView("administrator/course-selection");
        mv.getModel().put("coursesList", courseRepository.findAll());
        return mv;
    }

    @GetMapping("/course/{id}/requests")
    public ModelAndView getRequestsForCourse(@PathVariable(name = "id") long courseId){
        ModelAndView mv = new ModelAndView("/administrator/requests");
        Course selectedCourse = courseRepository.findOne(courseId);
        if(selectedCourse != null) {
            mv.getModel().put("course", selectedCourse);
            mv.getModel().put("studentsList", studentRepository.findBySignedUpCourses_CourseOrderBySignedUpCoursesTimestamp(selectedCourse));
        }else{
            // TODO: 21-Nov-17 Maybe raise a 404
        }
        return mv;
    }
}

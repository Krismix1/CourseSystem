package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.CourseRequest;
import dk.kea.dat16j.models.Student;
import dk.kea.dat16j.repositories.CourseRepository;
import dk.kea.dat16j.repositories.CourseRequestRepository;
import dk.kea.dat16j.repositories.StudentRepository;
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
import java.util.stream.Collectors;

/**
 * Created by Chris on 19-Nov-17.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseRequestRepository courseRequestRepository;

    @Autowired
    public StudentController(CourseRepository courseRepository, StudentRepository studentRepository, CourseRequestRepository courseRequestRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseRequestRepository = courseRequestRepository;
    }

    @GetMapping("/courses/sign-up")
    public ModelAndView getCourseSignUpPage(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Student student = studentRepository.findByAccount_Username(username);
        final List<Long> requestedCourses = student.getSignedUpCourses()
                .stream()
                .map(courseRequest -> courseRequest.getCourse().getId())
                .collect(Collectors.toList());


        ModelAndView mv = new ModelAndView("course/course-sign-up");


        if (requestedCourses != null && requestedCourses.size() > 0) {
            mv.getModel().put("coursesList", courseRepository.findAllByIdNotIn(requestedCourses));
        } else {
            mv.getModel().put("coursesList", courseRepository.findAll());
        }
        return mv;
    }

    @PostMapping("/courses/submit-sign-up")
    public ModelAndView submitSignUp(Authentication authentication,
                                     @RequestParam(name = "courses") Long[] coursesIds) {

        final Iterable<Course> courses = courseRepository.findAll(Arrays.asList(coursesIds));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Student student = studentRepository.findByAccount_Username(username);

        courses.forEach(student::addCourseSignUp);
        courseRequestRepository.save(student.getSignedUpCourses());
        studentRepository.save(student);

        return new ModelAndView(new RedirectView("/student/courses/sign-up"));
    }

    @GetMapping("/courses/signed-up/all")
    @ResponseBody // to allow students to see their requests
    public Collection<CourseRequest> getAllSignUpRequest(Authentication authentication) {
        ModelAndView mv = new ModelAndView();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Student student = studentRepository.findByAccount_Username(username);
        return student != null ? student.getSignedUpCourses() : null;
    }

    @GetMapping("requests/course")
    public ModelAndView getRequestsForCourse(@RequestParam long id) {
        Course course = courseRepository.findOne(id);

        Collection<Student> students = studentRepository.findBySignedUpCourses_Course(course); // TODO: 19-Nov-17 Check for null

        ModelAndView mv = new ModelAndView("course-sign-up-requests");
        mv.getModel().put("course", course); // TODO: 19-Nov-17 Check for null in the view
        mv.getModel().put("studentsList", students);

        return mv;
    }
}
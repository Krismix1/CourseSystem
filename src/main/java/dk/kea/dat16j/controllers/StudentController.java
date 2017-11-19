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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Chris on 19-Nov-17.
 */
@Controller
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
    public ModelAndView getCourseSignUpPage() {

        ModelAndView mv = new ModelAndView("course/course-sign-up");

        // TODO Send only the courses that the student didn't yet sign up for
        mv.getModel().put("coursesList", courseRepository.findAll());

        return mv;
    }

    @PostMapping("/courses/submit-sign-up")
    public ModelAndView submitSignUp(Authentication authentication,
                                     @RequestParam(name = "courses") Long[] coursesIds,
                                     @RequestParam String firstName,
                                     @RequestParam String lastName) {

        final Iterable<Course> courses = courseRepository.findAll(Arrays.asList(coursesIds));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
//        Student student = studentRepository.findByAccount_Username(username);
//        System.out.println(student);

        Student student = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        courses.forEach(student::addCourseSignUp);
        courseRequestRepository.save(student.getSignedUpCourses());
        studentRepository.save(student);

        return new ModelAndView(new RedirectView("/courses/sign-up"));
    }

    @GetMapping("/courses/signed-up/all")
    @ResponseBody
    public Collection<CourseRequest> getAllSignUpRequest(@RequestParam String firstName,
                                                         @RequestParam String lastName) {
        ModelAndView mv = new ModelAndView();

        final Student student = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        return student != null ? student.getSignedUpCourses() : null;
    }
}

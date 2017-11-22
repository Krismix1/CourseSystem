package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        // add courses, by id, that the student is already attending, so that they are filtered out, to remove confusion
        requestedCourses.addAll(student.getAttendingCourses().stream().map(Course::getId).collect(Collectors.toList()));

        ModelAndView mv = new ModelAndView("student/course-sign-up");

        if (requestedCourses != null && requestedCourses.size() > 0) {
            mv.getModel().put("coursesList", courseRepository.findAllByIdNotIn(requestedCourses));
        } else {
            mv.getModel().put("coursesList", courseRepository.findAll());
        }
        return mv;
    }

    @PostMapping("/courses/submit-sign-up")
    public ModelAndView submitSignUp(Authentication authentication,
                                     @RequestParam(name = "courses", required = false) Long[] coursesIds) {

        if (coursesIds != null && coursesIds.length > 0) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            Student student = studentRepository.findByAccount_Username(username);

            final Iterable<Course> courses = courseRepository.findAll(Arrays.asList(coursesIds));
            courses.forEach(student::addCourseSignUp);
            courseRequestRepository.save(student.getSignedUpCourses());
            studentRepository.save(student);
        } // else maybe redirect to /student/courses/sign-up?error=Nothing%20selected

        return new ModelAndView(new RedirectView("/student/courses/sign-up"));
    }

    @GetMapping("/courses/signed-up/all")
    public ModelAndView getAllSignUpRequest(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // TODO: 22-Nov-17 Null check? 
        String username = userDetails.getUsername();
        Student student = studentRepository.findByAccount_Username(username);

        ModelAndView mv = new ModelAndView("student/student-course-requests");
        mv.getModel().put("requestsList", student.getSignedUpCourses());

        return mv;
    }
}

package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.CourseRequest;
import dk.kea.dat16j.repositories.CourseRepository;
import dk.kea.dat16j.repositories.CourseRequestRepository;
import dk.kea.dat16j.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView getRequestsForCourse(@PathVariable(name = "id") long courseId) {
        ModelAndView mv = new ModelAndView("/administrator/requests");
        Course selectedCourse = courseRepository.findOne(courseId);
        if (selectedCourse != null) {
            mv.getModel().put("course", selectedCourse);
            // TODO: 21-Nov-17 Make a common Approve and Reject button for all students, rather than for each, and make each row as a checkbox
//            mv.getModel().put("requestsLists", studentRepository.findBySignedUpCourses_CourseOrderBySignedUpCoursesTimestamp(selectedCourse));
            mv.getModel().put("requestsLists", courseRequestRepository.findAllByCourseOrderByTimestamp(selectedCourse));
        } else {
            // TODO: 21-Nov-17 Maybe raise a 404
        }
        return mv;
    }

    @PostMapping("/request/approve")
    public String approveRequest(@RequestParam long requestId) {
        final CourseRequest request = courseRequestRepository.findOne(requestId);
        if (request != null) {
            if (request.getStudent().approveSignUpRequest(request)) {
                final Course course = request.getCourse();
                if (course.getAttendingStudents() < course.getMaximumNumberOfStudent()) {
                    course.incrementAttendingStudents(1);
                    courseRepository.save(course);
                    studentRepository.save(request.getStudent());
                    courseRequestRepository.delete(requestId);
                    return "redirect:/administrator/course/" + course.getId() + "/requests";
                } else {
                    throw new IllegalArgumentException("More than maximum students allowed: " + (course.getAttendingStudents() + 1));
                }
            } else {
                throw new RuntimeException("Could not approve request");
            }
        }
        return "redirect:/administrator/course-selection";
    }

    @PostMapping("/request/reject")
    public String rejectRequest(@RequestParam long requestId) {
        final CourseRequest request = courseRequestRepository.findOne(requestId);
        if (request != null) {
            request.getStudent().rejectSignUpRequest(request);
            courseRequestRepository.delete(requestId);
            return "redirect:/administrator/course/" + request.getCourse().getId() + "/requests";
        }
        return "redirect:/administrator/course-selection";
    }
}

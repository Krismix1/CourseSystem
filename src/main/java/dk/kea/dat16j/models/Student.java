package dk.kea.dat16j.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Chris on 13-Nov-17.
 */
@Entity
public class Student {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    private Collection<Course> attendingCourses; // this will store courses that the student is already attending
    @OneToMany(mappedBy = "student")
    private Collection<CourseRequest> signedUpCourses; // this will store courses where the student is waiting for approval
    @OneToOne
//    @Column(unique = true, nullable = false) // hibernate exception
    private Account account;
    // TODO: 13-Nov-17 Think about semesters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<Course> getAttendingCourses() {
        return attendingCourses;
    }

    public void setAttendingCourses(Collection<Course> attendingCourses) {
        this.attendingCourses = attendingCourses;
    }

    public Collection<CourseRequest> getSignedUpCourses() {
        return signedUpCourses;
    }

    public void setSignedUpCourses(Collection<CourseRequest> signedUpCourses) {
        this.signedUpCourses = signedUpCourses;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean addCourseSignUp(Course course) {
        if (signedUpCourses == null) {
            signedUpCourses = new ArrayList<>(1);
        }
        if (signedUpCourses.stream().noneMatch(c -> c.getId() == course.getId()) &&
                attendingCourses.stream().noneMatch(c -> c.getId() == course.getId())) {
            CourseRequest courseRequest = new CourseRequest();
            courseRequest.setCourse(course);
            courseRequest.setTimestamp(LocalDateTime.now());
            courseRequest.setStudent(this);
            return signedUpCourses.add(courseRequest);
        }
        return false;
    }

    public boolean approveSignUpRequest(CourseRequest courseRequest) {
        if (signedUpCourses != null) {
            if (signedUpCourses.removeIf(request -> request.getId() == courseRequest.getId())) { // This means that the student has such a request saved
                if (attendingCourses.stream().noneMatch(course -> course.getId() == courseRequest.getCourse().getId())) { // Because at the moment(22.11.2017 1:06AM) after the request
                    // is approved it gets deleted, that means that students can create a new request for the same course (by modifying the 'value' attribute of the input and submitting the form, or external tools)
                    return attendingCourses.add(courseRequest.getCourse());
                }
            }
        }
        return false;
    }

    public boolean rejectSignUpRequest(CourseRequest courseRequest) {
        if (signedUpCourses != null) {
            return signedUpCourses.removeIf(request -> request.getId() == courseRequest.getId());
        }
        return false;
    }
}

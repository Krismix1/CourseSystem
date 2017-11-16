package dk.kea.dat16j.models;

import javax.persistence.*;
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
    @ManyToMany
    private Collection<Course> signedUpCourses; // this will store courses where the student is waiting for approval
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

    public Collection<Course> getSignedUpCourses() {
        return signedUpCourses;
    }

    public void setSignedUpCourses(Collection<Course> signedUpCourses) {
        this.signedUpCourses = signedUpCourses;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

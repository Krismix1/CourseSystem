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
}

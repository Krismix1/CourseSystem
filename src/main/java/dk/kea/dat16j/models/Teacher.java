package dk.kea.dat16j.models;

import javax.persistence.*;

/**
 * Created by Chris on 13-Nov-17.
 */
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
//    @ManyToMany
//    private Collection<Course> teachingCourses;
    @OneToOne
//    @Column(unique = true, nullable = false) // hibernate exception
    private Account account;
}

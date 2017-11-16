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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

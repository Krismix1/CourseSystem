package dk.kea.dat16j.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * Created by Chris on 13-Nov-17.
 */
@Entity
public class StudyProgramme {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToMany
//    @JoinTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    @JsonBackReference
    private Collection<Course> courses;
}

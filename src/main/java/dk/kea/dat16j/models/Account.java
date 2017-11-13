package dk.kea.dat16j.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by Chris on 13-Nov-17.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue
    private long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Min(value = 6)
    @Max(value = 15)
    @Column(nullable = false)
    private String password; // TODO: 13-Nov-17 see if there is a Password class in Security module
//    private boolean enabled; // TODO: 13-Nov-17 security related, to be uncommented later
}

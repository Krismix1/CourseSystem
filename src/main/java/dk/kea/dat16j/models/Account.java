package dk.kea.dat16j.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
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
    private String username;

//    @Min(value = 6)
//    @Max(value = 15)
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "TINYINT default '1'", length = 1)
    private boolean enabled;
    @OneToOne
    private AccountRole accountRole;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Because in this Domain Logic the username name is an email
    public String getEmail() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }
}

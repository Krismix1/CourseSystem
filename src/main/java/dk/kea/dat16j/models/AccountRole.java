package dk.kea.dat16j.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Chris on 20-Nov-17.
 */
@Entity
public class AccountRole {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, nullable = false)
    private String roleName;

    public AccountRole() {
    }

    public AccountRole(String roleName) {
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

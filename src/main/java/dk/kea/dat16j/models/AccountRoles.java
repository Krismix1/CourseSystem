package dk.kea.dat16j.models;

/**
 * Created by Chris on 20-Nov-17.
 */
public enum AccountRoles {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER");

    private String role;

    AccountRoles(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString(){
        return role;
    }
}

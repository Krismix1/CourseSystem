package dk.kea.dat16j.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Chris on 19-Nov-17.
 */
@Entity
public class CourseRequest {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Course course;
    // TODO: 20-Nov-17 Add timestamp of request

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

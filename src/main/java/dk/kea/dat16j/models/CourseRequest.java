package dk.kea.dat16j.models;

import dk.kea.dat16j.configs.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Chris on 19-Nov-17.
 */
@Entity
public class CourseRequest {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @NotNull
    private Course course;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timestamp; // TODO: 21-Nov-17 Add timezone for timestamp, because this is the format that currently is saved "2017-11-21 00:26:48"
    @ManyToOne
//    @JoinTable()
    private Student student;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

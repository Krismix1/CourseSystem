package dk.kea.dat16j.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Chris on 13-Nov-17.
 */
@Entity
public class Course {
    @Id
    @GeneratedValue
    private long id;
    private String englishName;
    private String danishName;
//    @ManyToMany
//    private Collection<StudyProgramme> studyProgramme; // which study programmes will have this course
    @Column(columnDefinition = "TINYINT default '1'", length = 1)
    private boolean mandatory; // mandatory or elective
    private int ects;
    private String courseLanguage;
    private int minimumNumberOfStudent; // per semester?
    private int maximumNumberOfStudent; // per semester?
    private String prerequisites; // requirements for students, e.g. "Students must know HTML, CSS, JS, PHP and MySQL."
    private String learningOutcome;
    private String content;
    private String learningActivities;
    private String examForm; // TODO: 13-Nov-17 Maybe a new class?
    @ManyToMany
    private Collection<Teacher> teachers;
}

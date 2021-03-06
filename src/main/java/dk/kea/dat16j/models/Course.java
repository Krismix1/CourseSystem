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
//    private String classCode;
//    private short semester;
//    @ManyToMany
//    private Collection<StudyProgramme> studyProgramme; // which study programmes will have this course
    @Column(columnDefinition = "TINYINT default '1'", length = 1)
    private boolean mandatory; // mandatory or elective
    private int ects;
    private String courseLanguage;
    private int minimumNumberOfStudent;
    private int expectedNumberOfStudent;
    private int maximumNumberOfStudent;
    private int attendingStudents;
    private String prerequisites; // requirements for students, e.g. "Students must know HTML, CSS, JS, PHP and MySQL."
    private String learningOutcome;
    private String learningContent;
    private String learningActivities;
    private String examForm;
    @ManyToMany
    private Collection<Teacher> teachers;
    private String classCode;
    private int semester;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDanishName() {
        return danishName;
    }

    public void setDanishName(String danishName) {
        this.danishName = danishName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public int getMinimumNumberOfStudent() {
        return minimumNumberOfStudent;
    }

    public void setMinimumNumberOfStudent(int minimumNumberOfStudent) {
        this.minimumNumberOfStudent = minimumNumberOfStudent;
    }

    public int getExpectedNumberOfStudent() {
        return expectedNumberOfStudent;
    }

    public void setExpectedNumberOfStudent(int expectedNumberOfStudent) {
        this.expectedNumberOfStudent = expectedNumberOfStudent;
    }

    public int getMaximumNumberOfStudent() {
        return maximumNumberOfStudent;
    }

    public void setMaximumNumberOfStudent(int maximumNumberOfStudent) {
        this.maximumNumberOfStudent = maximumNumberOfStudent;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getLearningOutcome() {
        return learningOutcome;
    }

    public void setLearningOutcome(String learningOutcome) {
        this.learningOutcome = learningOutcome;
    }

    public String getLearningContent() {
        return learningContent;
    }

    public void setLearningContent(String learningContent) {
        this.learningContent = learningContent;
    }

    public String getLearningActivities() {
        return learningActivities;
    }

    public void setLearningActivities(String learningActivities) {
        this.learningActivities = learningActivities;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getAttendingStudents() {
        return attendingStudents;
    }

    public void setAttendingStudents(int attendingStudents) {
        this.attendingStudents = attendingStudents;
    }

    public int incrementAttendingStudents(int amount) {
        // TODO: 07-Dec-17 Check for overflow 
        attendingStudents += amount;
        // Hope that maximum and minimum is set before attending :)
        if (attendingStudents > maximumNumberOfStudent) {
            attendingStudents -= amount;
            throw new IllegalArgumentException("More than maximum students allowed: " + attendingStudents + amount);
        }
        return attendingStudents;
    }

    public boolean hasTeacher(Teacher teacher) {
        return teachers
                .stream()
                .anyMatch(t -> t.getId() == teacher.getId()); // TODO: 17-Nov-17 Add also check for usernames/emails being the same
    }
}

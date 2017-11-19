package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    //    Student findByAccount_Username(String username);
    Student findByFirstNameAndLastName(String firstName, String lastName);

    Collection<Student> findBySignedUpCourses_Course(Course course);
}

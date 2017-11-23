package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
    Collection<Course> findAllByIdNotIn(Collection<Long> courses);

    Collection<Course> findAllByTeachers(Teacher teacher);

    Collection<Course> findAllByEnglishNameContainingIgnoreCaseOrDanishNameContainingIgnoreCase(String englishName, String danishName);
}

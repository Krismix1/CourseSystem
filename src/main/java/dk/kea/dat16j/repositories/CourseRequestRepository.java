package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Course;
import dk.kea.dat16j.models.CourseRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Chris on 19-Nov-17.
 */
public interface CourseRequestRepository extends CrudRepository<CourseRequest, Long> {
    Collection<CourseRequest> findAllByCourseOrderByTimestamp(Course course);
}

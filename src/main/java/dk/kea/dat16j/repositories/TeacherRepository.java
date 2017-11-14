package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Teacher;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}

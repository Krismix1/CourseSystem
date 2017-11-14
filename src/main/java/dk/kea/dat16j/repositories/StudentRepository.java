package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Student;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
}

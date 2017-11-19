package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.StudyProgramme;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface StudyProgrammeRepository extends CrudRepository<StudyProgramme, Long> {
    StudyProgramme findByName(String name);

    Collection<StudyProgramme> findAllByNameIn(Collection<String> names);

    Collection<StudyProgramme> findAllByNameNotIn(Collection<String> names);
}

package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Chris on 14-Nov-17.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
}

package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.AccountRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Chris on 22-Nov-17.
 */
public interface AccountRoleRepository extends CrudRepository<AccountRole, Long> {
    AccountRole findByRoleName(String roleName);
}

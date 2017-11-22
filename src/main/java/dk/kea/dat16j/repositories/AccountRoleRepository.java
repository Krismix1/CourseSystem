package dk.kea.dat16j.repositories;

import dk.kea.dat16j.models.AccountRole;
import org.springframework.data.repository.CrudRepository;

public interface AccountRoleRepository extends CrudRepository<AccountRole, Long> {
    AccountRole findByRoleName(String roleName);
}

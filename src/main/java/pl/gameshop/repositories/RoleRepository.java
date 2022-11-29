package pl.gameshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gameshop.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByType(Role.Types type);
}

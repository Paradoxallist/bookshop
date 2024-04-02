package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName roleName);
}

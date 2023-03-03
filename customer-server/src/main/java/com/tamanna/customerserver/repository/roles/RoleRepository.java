package com.tamanna.customerserver.repository.roles;

import com.tamanna.customerserver.entity.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

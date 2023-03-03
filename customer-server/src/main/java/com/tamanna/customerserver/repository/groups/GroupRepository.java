package com.tamanna.customerserver.repository.groups;

import com.tamanna.customerserver.entity.groups.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
}

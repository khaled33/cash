package com.enda.cash.UserDetails.Dao.Repository;

import com.enda.cash.UserDetails.Dao.Entity.Role;
import com.enda.cash.UserDetails.Dao.Entity.RoleName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("roleApp")
public interface RolesRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRoleName(RoleName roleName);
}

package com.enda.cash.UserDetails.Dao.Repository;


import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.UserDetails.Dao.Entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUsersRepository extends JpaRepository<AppUser, Long> {
    public Optional<AppUser> findByUsername(String Username);

    public Optional<List<AppUser>> getAllByRoles_RoleName(RoleName Role);

    public Optional<AppUser> findByEmail(String email);
}

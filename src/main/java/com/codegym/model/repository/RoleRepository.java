package com.codegym.model.repository;

import com.codegym.model.entity.Role;
import com.codegym.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value = "select * " +
            "from roles r " +
            "where r.description like (:fullname);")
    List<Role> findByFullName(@Param("fullname") String fullname);
    Optional<Role> findByName(RoleName name);
}

package com.gmr.boot.repositories;

import com.gmr.boot.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    List<RoleEntity> findByRoleName(String roleName);

    List<RoleEntity> findInByRoleName(List<String> roleNames);

}

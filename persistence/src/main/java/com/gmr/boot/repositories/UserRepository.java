package com.gmr.boot.repositories;

import com.gmr.boot.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    void deleteByUsername(String username);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

}

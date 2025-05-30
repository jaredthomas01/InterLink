package com.example.InterLink.repository;


import com.example.InterLink.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
}

package com.example.InterLink.service;

import com.example.InterLink.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(Long id);
    UserEntity saveUser(UserEntity userEntity);
    UserEntity updateUser(Long id, UserEntity userEntity);
    Optional<UserEntity> findByEmail(String email);
    UserEntity login(String email, String password);
    void deleteUser(Long id);
}

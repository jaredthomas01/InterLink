package com.example.InterLink.service;

import com.example.InterLink.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(Long id);
    UserEntity saveUser(UserEntity userEntity);
    UserEntity updateUser(Long id, UserEntity userEntity);
    void deleteUser(Long id);
}

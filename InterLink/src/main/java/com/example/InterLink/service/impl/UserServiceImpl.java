package com.example.InterLink.service.impl;

import com.example.InterLink.entity.UserEntity;
import com.example.InterLink.repository.UserRepository;
import com.example.InterLink.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity userEntity) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(userEntity.getEmail());
            existingUser.setPassword(userEntity.getPassword());
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

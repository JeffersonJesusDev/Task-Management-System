package com.jdev.TaskManagementSystem.service;

import com.jdev.TaskManagementSystem.dto.UserDTO;
import com.jdev.TaskManagementSystem.model.User;
import com.jdev.TaskManagementSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<Void> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> updateUserById(Long userId, UserDTO userDTO) throws Exception {
        Optional<User> userOptionional = userRepository.findById(userId);
        if (userOptionional.isPresent()){
            User newUser = userOptionional.get();
            newUser.setName(userDTO.name());
            newUser.setEmail(userDTO.email());
            newUser.setPassword(userDTO.password());
            userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            throw new Exception("User not found");
        }
    }
}

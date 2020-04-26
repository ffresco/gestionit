/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.UserCreateFormDTO;
import com.gestionit.base.repository.UserRepo;
import com.gestionit.base.service.UserService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author fafre
 */
@Service
public class UserServiceImp implements UserService {

    //private final UserRepository userRepository;
    private final UserRepo userRepository;
        
    @Autowired
    public UserServiceImp(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        //return Optional.ofNullable(userRepository.getById(id));
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        //return Optional.of(userRepository.findOneByEmail(email));
        return Optional.of(userRepository.findByEmail(email));

    }

    @Override
    public Collection<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User create(UserCreateFormDTO form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(form.getPassword());
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        String userName = userDetails.getUsername();
        return userRepository.findByEmail(userName);
    }

}

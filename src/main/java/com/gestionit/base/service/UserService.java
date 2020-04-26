/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.UserCreateFormDTO;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author fafre
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateFormDTO form);
    
    User getCurrentUser();



}

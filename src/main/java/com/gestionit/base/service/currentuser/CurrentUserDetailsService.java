/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.currentuser;

import com.gestionit.base.domain.CurrentUser;
import com.gestionit.base.domain.User;
import com.gestionit.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author fafre
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService{

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentUser(user);
    }
    
    
}

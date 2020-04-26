/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;

import com.gestionit.base.domain.dto.UserCreateFormDTO;
import com.gestionit.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author fafre
 */
@Component
public class UserCreateFormValidator implements Validator {
    
    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }
    
    

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UserCreateFormDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
       UserCreateFormDTO ucfdto = (UserCreateFormDTO) o;
       validatePassword(errors,ucfdto);
       validateMail(errors,ucfdto);
       
               
    }

    private void validatePassword(Errors errors, UserCreateFormDTO ucfdto) {
        if (!ucfdto.getPassword().equals(ucfdto.getPasswordRepeated())) {
            errors.reject("password.no_match","Passwords do not match");
        }
    }

    private void validateMail(Errors errors, UserCreateFormDTO ucfdto) {
        if (userService.getUserByEmail(ucfdto.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exist");
        }
    }
    
}

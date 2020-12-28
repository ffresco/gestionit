/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;

import com.gestionit.base.domain.dto.UserCreateFormDTO;
import com.gestionit.base.service.UserService;
import com.gestionit.base.utils.PasswordConstraintValidator;

import java.util.Iterator;
import java.util.List;

import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
    	PasswordConstraintValidator validator = new PasswordConstraintValidator();
    	Pair<Boolean,  List<String>>  result = validator.isValid(ucfdto.getPassword());
        if (!ucfdto.getPassword().equals(ucfdto.getPasswordRepeated())) {
            errors.reject("password.no_match","La password no coincide");
        }
        if(!ucfdto.getPassword().isEmpty() && !result.getFirst()) {
        	for (Iterator<String> iterator = result.getSecond().iterator(); iterator.hasNext();) {
				String message = (String) iterator.next();
				errors.reject("password.error",message);
			}
        	
        }
    }

    private void validateMail(Errors errors, UserCreateFormDTO ucfdto) {
    	
        if (needValidationEmail(ucfdto) && userService.getUserByEmail(ucfdto.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exist");
        }
    }


    /**
     * 
     * @param ucfdto user dto
     * @return true if new user or the new mail is not equal to the original
     */
	private boolean needValidationEmail(UserCreateFormDTO ucfdto) {
		
		return ucfdto.getOriginalEmail()==null || !ucfdto.getOriginalEmail().equals(ucfdto.getEmail());
	}
    
}

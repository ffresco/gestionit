/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;


import com.gestionit.base.domain.dto.IncidenteDTO;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Cbova
 */
@Component
public class IncidenteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IncidenteDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
      
     
      }
    
}

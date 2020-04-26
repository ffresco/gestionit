/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.dto.CotizacionDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author fafre
 */
@Component
public class CotizacionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CotizacionDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
      CotizacionDTO cotDTO = (CotizacionDTO) target;
      Cotizacion cot = cotDTO.getCotizacion();
      
      if(cot.getCotizacionCmp()<0) {
          errors.reject("cotizacion.menorCro", "El valor ingresado es menor a cero");
      }
     
      }
    
}

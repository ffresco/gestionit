/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.domain.dto.UserCreateFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author fafre
 */
@Component
public class OperacionProcessValidator implements Validator {

    @Autowired
    DataMaster dataMaster;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(OperacionDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //casteo a operacion
        OperacionDTO opDTO = (OperacionDTO) target;
        Operacion op = opDTO.getOperacion();
        OperacionItem opO = opDTO.getOpO();
        OperacionItem opD = opDTO.getOpD();
        String monedaBase = dataMaster.getMonedaBase().getValor();
        
        if ((op.getTipoOp().getValor().equals("CMP"))||(op.getTipoOp().getValor().equals("VTA"))) {
            validateCmpVta(opO, opD, errors, monedaBase);
        }
        if (op.getTipoOp().getValor().equals("CANJE")) {
            System.out.println("Entro por canje");
        }
        if ((op.getTipoOp().getValor().equals("ARBITRAJE"))){
            System.out.println("entro por arbitraje");
        }
        
  

    }

   
    private void validateCmpVta(OperacionItem opO, OperacionItem opD, Errors errors, String monedaBase) {
        if (opO.getMoneda().getValor().equals(opD.getMoneda().getValor())) {
            errors.reject("operacion.igualMoneda", "La moneda de origen y destino son iguales");
        }
        
        if (!(opO.getMoneda().getValor().equals(monedaBase)
                || opD.getMoneda().getValor().equals(monedaBase))) {
            errors.reject("operacion.sinMonedaBase", "El origen o el destino debe estar expresado en moneda base = " + monedaBase);
        }
        
       
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.validator;

import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.dto.ClienteDTO;
import com.gestionit.base.domain.dto.CotizacionDTO;
import com.gestionit.base.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author fafre
 */
@Component
public class ClienteValidator implements Validator{

    @Autowired
    private ClienteService clienteService;
    
    @Override
    public boolean supports(Class<?> clazz) {
      return clazz.equals(ClienteDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
      ClienteDTO dto = (ClienteDTO) target;
      Cliente cliente = dto.getCliente();
      
      //si el cliente es nuevo o sea que se grava por primera vez
      if(cliente.getId()==null){
        if(clienteService.existClient(cliente)) errors.reject("cliente.documento.duplicado","El numero de documento ya existe");
      }
      //Inhabilitados
      //Terroristas
    }
    
}

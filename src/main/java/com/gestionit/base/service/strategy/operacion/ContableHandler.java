/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.strategy.operacion;

import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.dto.OperacionDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 *
 * @author fafre
 */
@Component
public class ContableHandler extends OperacionHandler{

    @Override
    public void generarOperacion(OperacionDTO opDTO, BindingResult result) {
        LOGGER.debug("Le pegue al Contable handler--------------" );

        //Busco los acumulados        
        AcumuladoCaja acumO = acr.findTopByMonedaIdAndInstrumentoIdAndCajaId(opDTO.getOpO().getMoneda().getId(),
                opDTO.getOpO().getInstrumento().getId(),opDTO.getOpO().getCaja().getId());
        AcumuladoCaja acumD = acr.findTopByMonedaIdAndInstrumentoIdAndCajaId(opDTO.getOpO().getMoneda().getId(),
                opDTO.getOpD().getInstrumento().getId(),opDTO.getOpD().getCaja().getId());
        
   
        //Si la cotizacion es null devolver un error
        if ((acumO == null) || (acumD == null)) {
            result.reject("operacion.no.acum", "no hay un saldo para esta seleccion se requiere desarrollar la creacion");
            return;
        }
        acumO.upadateEgreso(opDTO.getOpO().getMonto());
        acumO.upadateSaldo();
        acumD.upadateIngreso(opDTO.getOpO().getMonto());
        acumD.upadateSaldo();
        opDTO.setAcumCajaO(acumO);
        opDTO.setAcumCajaD(acumD);
        opDTO.getOpD().setMonto(opDTO.getOpO().getMonto());
   
    }
    
}

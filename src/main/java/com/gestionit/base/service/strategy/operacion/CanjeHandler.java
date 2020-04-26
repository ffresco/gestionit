/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.strategy.operacion;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.dto.OperacionDTO;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 *
 * @author fafre
 */
@Component
public class CanjeHandler extends OperacionHandler {

    @Override
    public void generarOperacion(OperacionDTO opDTO, BindingResult result) {
        System.out.println("Le pegue al handler de canje------------------");
        


        //busco ambas operaciones tienen igual moneda
        OperacionItem opI = opDTO.getOpO();

        //pido la cotizacion
        Cotizacion cot = cotizacionService.getLastCotizacionForOp(opDTO.getOperacion(), opI, dataMaster.getMonedaBase());
        //Si la cotizacion es null devolver un error
        if (cot == null) {
            result.reject("operacion.nocotizacion", "No hay ninguna cotizacion cargada para esta seleccion");
            return;
        }

        float montoOp;
        float cotizacionAplicada;
        cotizacionAplicada = cot.getCanje();
        montoOp = opDTO.getOpO().getMonto() * (1 - cotizacionAplicada);
        opDTO.getOpD().setMonto(montoOp);
        opDTO.getOpO().setCotizacion(cot);
        opDTO.getOperacion().setValCotAplicado(cotizacionAplicada);


    }

}

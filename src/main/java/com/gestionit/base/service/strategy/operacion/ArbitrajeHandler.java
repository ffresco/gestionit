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
public class ArbitrajeHandler extends OperacionHandler {

    @Override
    public void generarOperacion(OperacionDTO opDTO, BindingResult result) {
        System.out.println("Le pegue al Arbitraje handler--------------" );

        //Busco las cotizaciones        
        Cotizacion cotO = cotizacionService.getLastCotizacionForOp(opDTO.getOperacion(), opDTO.getOpO(), dataMaster.getMonedaBase());
        Cotizacion cotD = cotizacionService.getLastCotizacionForOp(opDTO.getOperacion(), opDTO.getOpD(), dataMaster.getMonedaBase());

        //Si la cotizacion es null devolver un error
        if ((cotO == null) || (cotD == null)) {
            result.reject("operacion.nocotizacion", "No hay ninguna cotizacion cargada para esta seleccion" + opDTO.getOpO().getMoneda().getValor()
                    + " --> $ o " + opDTO.getOpD().getMoneda().getValor() + " --> $");
            return;
        }

        float montoOp;
        float cotizacionAplicada;
        //la cotizacion para un canje es cotizacion compra origen/ cotizacion venta Destino
        cotizacionAplicada = (cotO.getCotizacionCmp() / cotD.getCotizacionVta());
        System.out.println("cotizacion a aplicar "+cotizacionAplicada);
        montoOp = opDTO.getOpO().getMonto() * cotizacionAplicada;
        opDTO.getOpD().setMonto(montoOp);
        opDTO.getOpO().setCotizacion(cotO);
        opDTO.getOpD().setCotizacion(cotD);
        opDTO.getOperacion().setValCotAplicado(cotizacionAplicada);

    }

  
}

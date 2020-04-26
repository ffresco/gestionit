/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.strategy.operacion;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.Parametro;
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
public class CmpVtaHandler extends OperacionHandler {

    @Override
    public void generarOperacion(OperacionDTO opDTO, BindingResult result) {
        System.out.println("Le pegue al handler de compra venta ---------------------");
      
        //busco la op con  moneda extranjera
        OperacionItem opI = this.getOperacionAUtilizar(opDTO.getOpO(), this.dataMaster.getMonedaBase(),opDTO.getOpD());
                
        Cotizacion cot = cotizacionService.getLastCotizacionForOp(opDTO.getOperacion(), opI,dataMaster.getMonedaBase());
        //Si la cotizacion es null devolver un error
        if (cot == null) {
            result.reject("operacion.nocotizacion", "No hay ninguna cotizacion cargada para esta seleccion");
            return;
        }

        float montoOp;
        float cotizacionAplicada;
        float montoMndBase;
        if (opDTO.isCompra()) {
            //si le compro la mnd ext -> mnd ext * cot.compra = mnd Base
            cotizacionAplicada = cot.getCotizacionCmp();
            montoOp = opDTO.getOpO().getMonto() * cotizacionAplicada;
            montoMndBase = montoOp;
            
        } else {
            //si le vendo moneda ext es --> monedabase Recibida / cot.vta = mnd Ext 
            cotizacionAplicada = cot.getCotizacionVta();
            montoOp = opDTO.getOpO().getMonto() / cotizacionAplicada;
            montoMndBase = opDTO.getOpO().getMonto();
        }
        System.out.println("a ver donde explota");
        opDTO.getOpD().setMonto(montoOp);
        opDTO.getOpO().setCotizacion(cot);
        opDTO.getOperacion().setValCotAplicado(cotizacionAplicada);
        opDTO.getOperacion().setMontoMndBase(montoMndBase);

    }
    
    /**
     * Para operaciones de compra venta se fija donde esta la moneda extranjera para calcular
     * y te devuelve la suboperacion que la contine
     * @param opO
     * @param monedaBase
     * @param opD
     * @return 
     */
     private OperacionItem getOperacionAUtilizar(OperacionItem opO, Parametro monedaBase, OperacionItem opD) {
        //busco la moneda extranjera, si el origen es igual a la moneda base, 
        //entonces la extranjera la tiene la op de destino
        if (opO.getMoneda().getValor().equals(monedaBase.getValor())) {
           return opD;
        }else{
           return opO;
        }
        
    }
    /**
     * Devuelve true si la cotizacion a tomar es la de compra
     * Devuelve false si la cotizacion a o tomar es de venta
     * Si lo que se recibe(origen) es Moneda Base --> es una Venta
     * Si lo que se recibe(origen) es Moneda Extranjera --> es una compra
     */
    public boolean iscompra(Parametro monedaO,Parametro monedaB){
        //si la mone
        boolean venta = (monedaO.getValor().equals(monedaB.getValor()));
        return !venta;
    }


}

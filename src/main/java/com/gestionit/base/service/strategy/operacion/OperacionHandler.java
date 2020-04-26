/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.strategy.operacion;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.controller.CajasController;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.repository.AcumuladoCajaRepo;
import com.gestionit.base.service.CotizacionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 *
 * @author fafre
 */


public abstract class OperacionHandler {
    
    @Autowired
    protected CotizacionService cotizacionService;
    @Autowired
    protected DataMaster dataMaster;
    @Autowired
    protected AcumuladoCajaRepo acr;

    protected static final Logger LOGGER = LoggerFactory.getLogger(OperacionHandler.class);
    
    /**
     * @return : a Binding Result object with error if they exist
     * @param opDTO
     * @param result 
     * Steps that thi method must acomplish in the implementation
     * 1-Get op origen if has moneda not base look for cotizacion available return result.reject if not cotizacion
     * set op origen cotizacion if exist, do the same with op destiono
     * 2-Depending on type of operation pick the right cotization, and set value to operacion
     * 3-Calculate monto to give
     */
    public abstract void generarOperacion(OperacionDTO opDTO, BindingResult result);
    

}

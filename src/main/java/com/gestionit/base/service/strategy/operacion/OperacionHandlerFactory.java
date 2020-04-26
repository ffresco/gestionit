/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service.strategy.operacion;

import com.gestionit.base.domain.Operacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author fafre
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OperacionHandlerFactory {

    @Autowired
    private ApplicationContext context;
  
    public OperacionHandler getHandler(String tipoOp) {

        if ((tipoOp.equals("CMP")) || (tipoOp.equals("VTA"))) {
            return context.getBean(CmpVtaHandler.class);
        }
        if (tipoOp.equals("CANJE")) {
            return context.getBean(CanjeHandler.class);
        }
        if (tipoOp.equals("ARBITRAJE")) {
            return context.getBean(ArbitrajeHandler.class);
        }
        if (tipoOp.equals("CONTABLE")) {
            return context.getBean(ContableHandler.class);
        }
        return null;

    }
    
    
    public static boolean isCompra(Operacion op){
        return op.getTipoOp().getValor().equals("CMP");
    }
    
    public static boolean isVenta(Operacion op){
        return op.getTipoOp().getValor().equals("VTA");
    }
    
    public static boolean isArbitraje(Operacion op){
        return op.getTipoOp().getValor().equals("ARBITRAJE");                
    }
    
    public static boolean isCanje(Operacion op){
        return op.getTipoOp().getValor().equals("CANJE");
    }
}

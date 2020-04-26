/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.enums;

import com.gestionit.base.domain.Parametro;

/**
 *
 * @author fafre
 */
public enum Estado {
    
    NUEVO(new Parametro("NUEVO","ESTADO"),1),
    PROCESADO(new Parametro("PROCESADO","ESTADO"),2),
    OK(new Parametro("OK","ESTADO"),3),
    OBSERVADO(new Parametro("OBSERVADO","ESTADO"),4);
    
    private final Parametro estado;
    private final int orden;
     
    private Estado(Parametro estado, int orden) {
        this.estado = estado;
        this.orden = orden;
    }

    public Parametro getEstado() {
        return estado;
    }

    public int getOrden() {
        return orden;
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Cotizacion;
import java.io.Serializable;


/**
 *
 * @author fafre
 */

public class CotizacionDTO implements Serializable{
    
    private Cotizacion cotizacion;
    private String fecha;

    public CotizacionDTO() {
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    

    @Override
    public String toString() {
        return "CotizacionDTO{" + "cotizacion=" + cotizacion + '}';
    }
 
    
   
    
}

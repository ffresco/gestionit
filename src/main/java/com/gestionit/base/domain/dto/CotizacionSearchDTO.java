/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;



/**
 *
 * @author fafre
 */
public class CotizacionSearchDTO {
    
    private String tipoOp;
    private String moneda;
    private String tipoCmb;
    private String instrumento;
    private String fechaDesde;
    private String entidad;

    public CotizacionSearchDTO() {
        
    }

    public String getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(String tipoOp) {
        this.tipoOp = tipoOp;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipoCmb() {
        return tipoCmb;
    }

    public void setTipoCmb(String tipoCmb) {
        this.tipoCmb = tipoCmb;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "CotizacionSearchDTO{" + "tipoOp=" + tipoOp + ", moneda=" + moneda + ", tipoCmb=" + tipoCmb + ", instrumento=" + instrumento + ", fechaDesde=" + fechaDesde + ", entidad=" + entidad + '}';
    }
    
    

    
    
    
}

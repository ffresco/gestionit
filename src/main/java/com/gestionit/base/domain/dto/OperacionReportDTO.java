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
public class OperacionReportDTO {
    
    private Long id;
    private String fechaHora;
    private String operacion;
    
    //recibido
    private String cajero;
    private String monedaR;
    private String instrumentoR;
    private float cantidadR;
    private float cotizacion;
    
    //entregado
    private String cliNombre;
    private String cliTipoDoc;
    private String cliNumDoc;
    private String domicilio;
    private String nacionalidad;
    private String monedaE;
    private String instrumentoE;
    private  float cantidadE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getMonedaR() {
        return monedaR;
    }

    public void setMonedaR(String monedaR) {
        this.monedaR = monedaR;
    }

    public String getInstrumentoR() {
        return instrumentoR;
    }

    public void setInstrumentoR(String instrumentoR) {
        this.instrumentoR = instrumentoR;
    }

    public float getCantidadR() {
        return cantidadR;
    }

    public void setCantidadR(float cantidadR) {
        this.cantidadR = cantidadR;
    }

    public float getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(float cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliTipoDoc() {
        return cliTipoDoc;
    }

    public void setCliTipoDoc(String cliTipoDoc) {
        this.cliTipoDoc = cliTipoDoc;
    }

    public String getCliNumDoc() {
        return cliNumDoc;
    }

    public void setCliNumDoc(String cliNumDoc) {
        this.cliNumDoc = cliNumDoc;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getMonedaE() {
        return monedaE;
    }

    public void setMonedaE(String monedaE) {
        this.monedaE = monedaE;
    }

    public String getInstrumentoE() {
        return instrumentoE;
    }

    public void setInstrumentoE(String instrumentoE) {
        this.instrumentoE = instrumentoE;
    }

    public float getCantidadE() {
        return cantidadE;
    }

    public void setCantidadE(float cantidadE) {
        this.cantidadE = cantidadE;
    }
    
    
    
}

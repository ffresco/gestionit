/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author fafre
 */
@Entity
@Table(name="cotizacion")
public class Cotizacion implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="fecha")    
    private LocalDateTime fecha;
    
    @OneToOne
    @JoinColumn(name="fk_moneda")
    private Parametro moneda;
    
    @OneToOne
    @JoinColumn(name="fk_entidad")
    private Parametro entidad;
    
    @OneToOne
    @JoinColumn(name="fk_tipoCambio")
    private Parametro tipoCambio;
    
    
    @Column(name="cotizacion_vta")
    private Float cotizacionVta;
    
    @Column(name="cotizacion_cmp")
    private Float cotizacionCmp;
    
    @Column(name="comision_vta")
    private Float comisionVta;
    
    @Column(name="comision_cmp")
    private Float comisionCmp;
    
    //A que valor tomo la moneda esta expresado en la misma moneda que moneda
    @Column(name="cotizacion_canje")
    private Float canje;
    
    @OneToOne
    @JoinColumn(name="fk_instrumento")
    private Parametro instrumento;
    
        
    @OneToOne
    @JoinColumn(name="fk_moneda_base")
    private Parametro monedaBase;
    
 

    public Cotizacion(LocalDateTime fecha, Parametro moneda, Parametro entidad, Parametro tipoCambio, Float cotizacionVta, Float cotizacionCmp, Float comisionVta, Float comisionCmp, Parametro instrumento, Parametro monedaBase, Float canje) {
        this.fecha = fecha;
        this.moneda = moneda;
        this.entidad = entidad;
        this.tipoCambio = tipoCambio;
        this.cotizacionVta = cotizacionVta;
        this.cotizacionCmp = cotizacionCmp;
        this.comisionVta = comisionVta;
        this.comisionCmp = comisionCmp;
        this.instrumento = instrumento;
        this.monedaBase = monedaBase;
        this.canje = canje;
    }

   

    public Cotizacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Parametro getMoneda() {
        return moneda;
    }

    public void setMoneda(Parametro moneda) {
        this.moneda = moneda;
    }

    public Parametro getEntidad() {
        return entidad;
    }

    public void setEntidad(Parametro entidad) {
        this.entidad = entidad;
    }

    public Parametro getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Parametro tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Float getCotizacionVta() {
        return cotizacionVta;
    }

    public void setCotizacionVta(Float cotizacionVta) {
        this.cotizacionVta = cotizacionVta;
    }

    public Float getCotizacionCmp() {
        return cotizacionCmp;
    }

    public void setCotizacionCmp(Float cotizacionCmp) {
        this.cotizacionCmp = cotizacionCmp;
    }

    public Float getComisionVta() {
        return comisionVta;
    }

    public void setComisionVta(Float comisionVta) {
        this.comisionVta = comisionVta;
    }

    public Float getComisionCmp() {
        return comisionCmp;
    }

    public void setComisionCmp(Float comisionCmp) {
        this.comisionCmp = comisionCmp;
    }

    public Parametro getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Parametro instrumento) {
        this.instrumento = instrumento;
    }

 
    public Parametro getMonedaBase() {
        return monedaBase;
    }

    public void setMonedaBase(Parametro monedaBase) {
        this.monedaBase = monedaBase;
    }

    

    public Float getCanje() {
        return canje;
    }

    public void setCanje(Float canje) {
        this.canje = canje;
    }

    @Override
    public String toString() {
        return "Cotizacion{" + "id=" + id + ", fecha=" + fecha + ", moneda=" + moneda + ", entidad=" + entidad + ", tipoCambio=" + tipoCambio + ", cotizacionVta=" + cotizacionVta + ", cotizacionCmp=" + cotizacionCmp + ", comisionVta=" + comisionVta + ", comisionCmp=" + comisionCmp + ", canje=" + canje + ", instrumento=" + instrumento + ", monedaBase=" + monedaBase + '}';
    }

    

    
    
    

   
    
    
}

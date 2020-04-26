/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fafre
 */
@Entity
@Table(name = "operacion_item")
public class OperacionItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "fk_operacion_id")
    private Operacion operacion;
    
    @OneToOne
    @JoinColumn(name = "fk_moneda",nullable = false)
    private Parametro moneda;
    
    @OneToOne
    @JoinColumn(name = "fk_instrumnto",nullable = false)   
    private Parametro instrumento;
    
    @OneToOne
    @JoinColumn(name = "fk_cotizacion_id")
    private Cotizacion cotizacion;

    @Column(name = "monto_rec_vlt")
    private Float montoRecVlt;
    
    @Column(name = "monto")
    private Float monto;
    
    @OneToOne    
    @JoinColumn(name="fk_caja_id")
    private Parametro caja;
    
    @Column(name = "fuente")
    private String fuente;
    
    @OneToOne    
    @JoinColumn(name = "fk_movimiento" )
    private Parametro movimiento;
    
    @Column(name = "orden")
    private int orden;

    public OperacionItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    public Parametro getMoneda() {
        return moneda;
    }

    public void setMoneda(Parametro moneda) {
        this.moneda = moneda;
    }

    public Parametro getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Parametro instrumento) {
        this.instrumento = instrumento;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Parametro getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Parametro movimiento) {
        this.movimiento = movimiento;
    }

    public Float getMontoRecVlt() {
        return montoRecVlt;
    }

    public void setMontoRecVlt(Float montoRecVlt) {
        this.montoRecVlt = montoRecVlt;
    }

    
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Parametro getCaja() {
        return caja;
    }

    public void setCaja(Parametro caja) {
        this.caja = caja;
    }
    
    

    public OperacionItem(Operacion operacion, Parametro moneda, Parametro instrumento, Float montoRecVlt, 
            Float monto, String fuente, Parametro movimiento, int orden,Cotizacion cot, Parametro
                    caja) {
        this.operacion = operacion;
        this.moneda = moneda;
        this.instrumento = instrumento;
        this.montoRecVlt = montoRecVlt;
        this.monto = monto;
        this.fuente = fuente;
        this.movimiento = movimiento;
        this.orden = orden;
        this.cotizacion = cot;
        this.caja = caja;
    }
    
    
    
    



    @Override
    public String toString() {
        String idOp = operacion==null?"":operacion.getId()+"";
        return "OperacionItem{" + "operacion=" + Optional.ofNullable(idOp) + ", moneda=" + Optional.ofNullable(moneda) + 
                ", instrumento=" + Optional.ofNullable(instrumento) + ", monto=" + monto + ", fuente=" + Optional.ofNullable(fuente) + 
                ", movimiento=" + Optional.ofNullable(movimiento) + ", orden=" + orden + "'}'";
    }
    
    
    
}

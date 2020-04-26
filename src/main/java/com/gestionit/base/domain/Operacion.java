/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fafre
 */

@Entity
@Table(name = "operacion")
public class Operacion implements Serializable{
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="fecha_hora")    
    private LocalDateTime fechaHora;
    
    @OneToOne
    @JoinColumn(name = "fk_cli_id")
    private Cliente cliente;
    
    
    @OneToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
    

    @OneToOne
    @JoinColumn(name = "fk_caja_id")
    private Parametro caja;
    
    @OneToOne
    @JoinColumn(name = "fk_tipo_op")
    private Parametro tipoOp;
    
    @OneToOne
    @JoinColumn(name = "fk_tipo_cam")
    private Parametro tipoCambio;
    
    @Column(name = "tipo_mov")
    private String tipoMov;
    
    @Column(name = "valor_cotizacion_aplicado")
    private Float valCotAplicado;
    
    @Column(name = "monto_mnd_base_op")
    private Float montoMndBase;
    
    @OneToOne
    @JoinColumn(name = "estado")
    private Parametro estado;
    
   
    @Column(name="observacion")    
    private String observaciones;
    
        
    @OneToMany(mappedBy = "operacion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OperacionItem> operacionItems;

    public Operacion() {
    }

    public Operacion( LocalDateTime fechaHora, Cliente cliente, Parametro caja, Parametro tipoOp, Parametro tipoCambio, 
            Parametro estado, String observaciones, List<OperacionItem> operacionItems, Float valCot) {

        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.caja = caja;
        this.tipoOp = tipoOp;
        this.tipoCambio = tipoCambio;
        this.estado = estado;
        this.observaciones = observaciones;
        this.operacionItems = operacionItems;
        this.valCotAplicado = valCot;
    }

    public Operacion(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Parametro getCaja() {
        return caja;
    }

    public void setCaja(Parametro caja) {
        this.caja = caja;
    }

    public Parametro getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(Parametro tipoOp) {
        this.tipoOp = tipoOp;
    }

    public Float getValCotAplicado() {
        return valCotAplicado;
    }

    public void setValCotAplicado(Float valCotAplicado) {
        this.valCotAplicado = valCotAplicado;
    }

  

    public Parametro getEstado() {
        return estado;
    }

    public void setEstado(Parametro estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<OperacionItem> getOperacionItems() {
        return operacionItems;
    }

    public void setOperacionItems(List<OperacionItem> operacionItems) {
        this.operacionItems = operacionItems;
    }

    public Parametro getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Parametro tipoCambio) {
        this.tipoCambio = tipoCambio;
    }
    
    public OperacionItem getOpItemO(){
        if (!operacionItems.isEmpty()) {
        	return operacionItems.get(0);
        }
        return null;
    }
    
    public OperacionItem getOpItemD(){
        if (!operacionItems.isEmpty()) {
        	return operacionItems.get(operacionItems.size()-1);
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Float getMontoMndBase() {
        return montoMndBase;
    }

    public void setMontoMndBase(Float montoMndBase) {
        this.montoMndBase = montoMndBase;
    }


    
    
    
    

    @Override
    public String toString() {
        return "Operacion{" + "id=" + id + ", fechaHora=" + fechaHora + ", cliente=" + Optional.ofNullable(cliente) + 
                ", caja=" + Optional.ofNullable(caja) + ", tipoOp=" + Optional.ofNullable(tipoOp) + ", tipoCambio=" + 
                Optional.ofNullable(tipoCambio) + ", cotizacion=" + Optional.ofNullable(valCotAplicado) + ", estado=" + 
                Optional.ofNullable(estado) + ", observaciones=" + observaciones + ", operacionItems=" +
                Optional.ofNullable(operacionItems) + '}';
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
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
import javax.persistence.Version;

/**
 *
 * @author fafre
 */
@Entity
@Table(name = "acumulado_cli")
public class AcumuladoCliente implements Serializable {
    
    @Version
    private long version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name="fk_cli_id")
    private Cliente cliente;
    
    @Column(name="mes")
    private Month mes;
    
    @Column(name="ano")
    private int ano;
    
    @Column(name="acum_mes")
    private Float acumuladoMes;
    
    @Column(name="acum_ano")
    private Float acumuladoAno;
    
    @OneToOne
    @JoinColumn(name="moneda_id")
     private Parametro moneda;
    
    @Column(name="fecha_ult_op")
    private LocalDateTime fechaUltimaOp;

    public AcumuladoCliente(Cliente cliente, Month mes, int ano, Float acumuladoMes, Float acumuladoAno, Parametro moneda, LocalDateTime fechaUltimaOp) {
        this.cliente = cliente;
        this.mes = mes;
        this.ano = ano;
        this.acumuladoMes = acumuladoMes;
        this.acumuladoAno = acumuladoAno;
        this.moneda = moneda;
        this.fechaUltimaOp = fechaUltimaOp;
    }

    public AcumuladoCliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Month getMes() {
        return mes;
    }

    public void setMes(Month mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Float getAcumuladoMes() {
        return acumuladoMes;
    }

    public void setAcumuladoMes(Float acumuladoMes) {
        this.acumuladoMes = acumuladoMes;
    }

    public Float getAcumuladoAno() {
        return acumuladoAno;
    }

    public void setAcumuladoAno(Float acumuladoAno) {
        this.acumuladoAno = acumuladoAno;
    }

    public Parametro getMoneda() {
        return moneda;
    }

    public void setMoneda(Parametro moneda) {
        this.moneda = moneda;
    }

    public LocalDateTime getFechaUltimaOp() {
        return fechaUltimaOp;
    }

    public void setFechaUltimaOp(LocalDateTime fechaUltimaOp) {
        this.fechaUltimaOp = fechaUltimaOp;
    }

    @Override
    public String toString() {
        Long cliId = cliente!=null?cliente.getId():-1L;
        return "AcumuladoCliente{" + "id=" + id + ", cliente=" + cliId +  ", mes=" + mes + ", ano=" + ano + ", acumuladoMes=" + acumuladoMes + ", acumuladoAno=" + acumuladoAno + ", moneda=" + moneda + ", fechaUltimaOp=" + fechaUltimaOp + '}';
    }
    
    
    
    
}

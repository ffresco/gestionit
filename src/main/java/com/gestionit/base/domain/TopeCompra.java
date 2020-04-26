/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author fafre
 */
@Entity
@Table(name="tope_operacion")
public class TopeCompra implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    
        
    @Column(name = "tope_mes")
    private Float topeMes;
    
    @Column(name = "tope_ano")
    private Float topeAno;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;
    
    //Agregar una entidad cuando reodri termine

    public TopeCompra(Float topeMes, Float topeAno, LocalDateTime fechaAlta) {
        this.topeMes = topeMes;
        this.topeAno = topeAno;
        this.fechaAlta = fechaAlta;
    }
    

    public TopeCompra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTopeMes() {
        return topeMes;
    }

    public void setTopeMes(Float topeMes) {
        this.topeMes = topeMes;
    }

    public Float getTopeAno() {
        return topeAno;
    }

    public void setTopeAno(Float topeAno) {
        this.topeAno = topeAno;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "TopeCompra{" + "id=" + id + ", topeMes=" + topeMes + ", topeAno=" + topeAno + ", fechaAlta=" + fechaAlta + '}';
    }

  
  


    
    
    
    
    
    
}

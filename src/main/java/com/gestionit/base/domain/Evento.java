/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


/**
 *
 * @author Cbova
 */

@Entity
public class Evento implements Serializable{
  
    /**
	 * 
	 */
	private static final long serialVersionUID = -6357037372723726234L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

	@Column(name="fecha")    
    private LocalDate fecha;
    
    
    @OneToOne
    @JoinColumn(name = "fk_riesgo_id")
    private Riesgo riesgo;
    
    private String descripcion;
    
    
   
    public Evento() {
    }

    public Evento(Long id, LocalDate fecha, String descripcion, Riesgo riesgo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.riesgo = riesgo;
		this.descripcion = descripcion;
	}

    public Evento(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

	public Riesgo getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(Riesgo riesgo) {
		this.riesgo = riesgo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
    @Override
    public String toString() {
        return "Evento{" + "id=" + id + ", fecha=" + fecha + ", descripcion=" + 
                Optional.ofNullable(descripcion)  + '}';
    }

    
    
    
}

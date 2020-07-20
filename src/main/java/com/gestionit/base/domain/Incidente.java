/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Incidente implements Serializable{
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

	@Column(name="fecha_hora")    
    private LocalDateTime fechaHora;
    
    @Column(name="fecha_cierre")    
    private LocalDateTime fechaHoraCierre;
    
    @OneToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
    

    @OneToOne
    @JoinColumn(name = "fk_clasificacion_id")
    private Parametro clasificacion;
    
    @OneToOne
    @JoinColumn(name = "fk_tipo_inc")
    private Parametro tipoInc;
    
    @Column(name="responsable_resolucion")
    private String responsableResolucion;
    
    private String solucion;
    
    private String descripcion;
    
    private Boolean activa;
    
   
    public Incidente() {
    }

    public Incidente(Long id, LocalDateTime fechaHora, LocalDateTime fechaHoraCierre, User user,
			Parametro clasificacion, Parametro tipoInc, String responsabelResolucion, String solucion,
			String descripcion, Boolean activa) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.fechaHoraCierre = fechaHoraCierre;
		this.user = user;
		this.clasificacion = clasificacion;
		this.tipoInc = tipoInc;
		this.responsableResolucion = responsabelResolucion;
		this.solucion = solucion;
		this.descripcion = descripcion;
		this.activa = activa;
	}

    public Incidente(Long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Incidente{" + "id=" + id + ", fechaHora=" + fechaHora + ", Usuario=" + Optional.ofNullable(user) + 
                ", clasificacion=" + Optional.ofNullable(clasificacion) + ", tipoInc=" + Optional.ofNullable(tipoInc) + ", solucion=" + 
                Optional.ofNullable(solucion) + ", activa=" + Optional.ofNullable(activa) + ", descripcion=" + 
                Optional.ofNullable(descripcion)  + '}';
    }
    
    
    
    
}

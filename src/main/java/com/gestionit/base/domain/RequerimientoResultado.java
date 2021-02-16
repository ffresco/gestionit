/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;




/**
 *
 * @author Cbova
 */

@Entity
public class RequerimientoResultado implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7987407831809319024L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
 
    private Boolean conforme;
    
    private String comentarios;
    
    @Column(name="nombre_participante")
    private String nombreParticipante;
    
    @ManyToOne
    @JoinColumn(name = "fk_requerimiento", nullable = false, updatable = false)
    private Requerimiento requerimiento;

	public Long getId() {
		return id;
	}

	public Boolean getConforme() {
		return conforme;
	}

	public String getComentarios() {
		return comentarios;
	}

	public String getNombreParticipante() {
		return nombreParticipante;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConforme(Boolean conforme) {
		this.conforme = conforme;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public void setNombreParticipante(String nombreParticipante) {
		this.nombreParticipante = nombreParticipante;
	}
    
  
   
}

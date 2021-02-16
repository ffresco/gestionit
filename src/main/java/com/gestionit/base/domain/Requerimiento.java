/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;





/**
 *
 * @author Cbova
 */

@Entity
public class Requerimiento implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2538675987078788902L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
 
    private String solicitante;
    
    private String area;
    
    private String categoria;
    
    @Column(name="tipo_de_solicitud")
    private String tipoSolicitud;
    
    @Column(name="tipo_de_severidad")
    private String tipoSeveridad;
    
    private String Detalle;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requerimiento")
    private List<RequerimientoResultado> resultados;

	public Long getId() {
		return id;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public String getArea() {
		return area;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public String getTipoSeveridad() {
		return tipoSeveridad;
	}

	public String getDetalle() {
		return Detalle;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public void setTipoSeveridad(String tipoSeveridad) {
		this.tipoSeveridad = tipoSeveridad;
	}

	public void setDetalle(String detalle) {
		Detalle = detalle;
	}

	public List<RequerimientoResultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<RequerimientoResultado> resultados) {
		this.resultados = resultados;
	}
    
   
}

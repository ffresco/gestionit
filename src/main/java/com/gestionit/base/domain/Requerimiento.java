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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;





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
    
    @OneToOne
    @JoinColumn(name="fk_area")
    private Parametro area;

    
    @OneToOne
    @JoinColumn(name="fk_categoria")
    private Parametro categoria;

    
    @OneToOne
    @JoinColumn(name="fk_tipo_de_solicitud")
    private Parametro tipoSolicitud;
    
   
    @OneToOne
    @JoinColumn(name="fk_tipo_de_severidad")
    private Parametro tipoSeveridad;

    
    private String Detalle;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requerimiento", orphanRemoval = true)
    private List<RequerimientoResultado> resultados;

	public Long getId() {
		return id;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public Parametro getArea() {
		return area;
	}

	public Parametro getCategoria() {
		return categoria;
	}

	public Parametro getTipoSolicitud() {
		return tipoSolicitud;
	}

	public Parametro getTipoSeveridad() {
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

	public void setArea(Parametro area) {
		this.area = area;
	}

	public void setCategoria(Parametro categoria) {
		this.categoria = categoria;
	}

	public void setTipoSolicitud(Parametro tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public void setTipoSeveridad(Parametro tipoSeveridad) {
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

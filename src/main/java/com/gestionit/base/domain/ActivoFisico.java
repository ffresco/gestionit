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
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;




/**
 *
 * @author Cbova
 */

@Entity
@Audited
public class ActivoFisico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -747604991913308145L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
 
    private String codigo;
    
    private String nombre;
    
    @OneToOne
    @JoinColumn(name="fk_tipo")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Parametro tipo;
    
    @OneToOne
    @JoinColumn(name="fk_ubicacion")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Parametro ubicacion;
    
    private String descripcion;
    
    private String propietario;
    
    private String cargo;
    
	@Column(name="fecha_clasificacion")
	private LocalDate fechaClasificacion;
    

    @OneToOne
    @JoinColumn(name="fk_clasificacion_informacion")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Parametro clasificacionInformacion;  //clasificacion de la informacion
    
    
    @OneToOne
    @JoinColumn(name="fk_sector")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Parametro sector;
    
    
    
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "activo_fisico_riesgo",
            joinColumns = {@JoinColumn(name = "activo_fisico_id")},
            inverseJoinColumns = {@JoinColumn(name = "riesgo_id")}
    )
    private List<Riesgo> riesgos;
    
    private Integer riesgoResidual;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public Parametro getTipo() {
		return tipo;
	}

	public void setTipo(Parametro tipo) {
		this.tipo = tipo;
	}

	public Parametro getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(Parametro ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}


	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public Parametro getSector() {
		return sector;
	}

	public void setSector(Parametro sector) {
		this.sector = sector;
	}


	@Override
    public String toString() {
        return "Activo Fisico{" + "id=" + id + ", Codigo=" + codigo + ", Origen=" + Optional.ofNullable(nombre) + 
                ", Tipo=" + Optional.ofNullable(tipo) +'}';
    }


	public Parametro getClasificacionInformacion() {
		return clasificacionInformacion;
	}


	public void setClasificacionInformacion(Parametro clasificacionInformacion) {
		this.clasificacionInformacion = clasificacionInformacion;
	}


	public LocalDate getFechaClasificacion() {
		return fechaClasificacion;
	}


	public void setFechaClasificacion(LocalDate fechaClasificacion) {
		this.fechaClasificacion = fechaClasificacion;
	}


	public List<Riesgo> getRiesgos() {
		return riesgos;
	}


	public void setRiesgos(List<Riesgo> riesgos) {
		this.riesgos = riesgos;
	} 

    public Integer getRiesgoResidual() {
    	return riesgoResidual;
    }


	public void setRiesgoResidual(Integer riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}
}

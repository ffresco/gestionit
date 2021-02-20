package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;


/**
 * Representa un proyecto
 * @author cbova
 *
 */

@Entity
@Table(name="proyecto")
@Audited
public class Proyecto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7569675864497898725L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
	
	@Column(name="objetivo")
	private String objetivo;
	
	@Column(name="fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name="fecha_fin")
	private LocalDate fechaFin;
	
	@Column(name="costo_estimado")
	private Double costoEstimado;
	
	@Column(name="recursos_tecnicos")
	private String recursosTecnicos;
	
	@Column(name="recursos_humanos")
	private String recursosHumanos;
	
	@Column(name="alcance")
	private String alcance;

	
	@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "proyecto_riesgo",
            joinColumns = {@JoinColumn(name = "proyecto_id")},
            inverseJoinColumns = {@JoinColumn(name = "riesog_id")}
    )
	private List<Riesgo> riesgos;
	
    
	
	
	
	public List<Riesgo> getRiesgos() {
		return riesgos;
	}

	public void setRiesgo(List<Riesgo> riesgos) {
		this.riesgos = riesgos;
	}


	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}


	public Double getCostoEstimado() {
		return costoEstimado;
	}

	public void setCostoEstimado(Double costoEstimado) {
		this.costoEstimado = costoEstimado;
	}

	public String getRecursosTecnicos() {
		return recursosTecnicos;
	}

	public void setRecursosTecnicos(String recursosTecnicos) {
		this.recursosTecnicos = recursosTecnicos;
	}

	public String getRecursosHumanos() {
		return recursosHumanos;
	}

	public void setRecursosHumanos(String recursosHumanos) {
		this.recursosHumanos = recursosHumanos;
	}

	public String getAlcance() {
		return alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	
	

}

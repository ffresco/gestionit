/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


/**
 *
 * @author Cbova
 */
@Entity
@Table(name="riesgo")
@Audited
public class Riesgo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="fecha_analisis")    
    private LocalDate fechaAnalisis;
    
    private String codigoRiesgo;
    
    private String descripcion;
    
    @Column(name="codigo_formulario")
    private String codigoFormulario;
    
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="fk_amenaza")
    private Amenaza amenaza;
    
    private String responsable;
    
    @Column(name="afecta_confiabilidad")
    private Boolean afectaConfidencialidad;
    
    @Column(name="afecta_integridad")
    private Boolean afectaIntegridad;
    
    @Column(name="afecta_disponibilidad")
    private Boolean afectaDisponibilidad;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="fk_impacto")
    private Impacto impacto;
    
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="fk_prob_ocur")
    private ProbalidadOcurrencia probabilidadOcurrencia;
    
    @Audited
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="fk_riesgo_inherente_valor")
    private RiesgoInherenteValor riesgoInherenteValor;
    
    @Audited
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="fk_salvaguarda")
    private Salvaguarda salvaguarda;
    
    @Audited
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="fk_riesgo_residual_valor")
    private RiesgoResidualValor riesgoResidualValor;
    
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="fk_usuario_creador")
    private User usuarioCreador;
    

    @OneToOne
    @JoinColumn(name="fk_usuario_auditor")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private User usuarioAuditor;
    
    @Column(name="fecha_aprobacion")    
    private LocalDate fechaAprobacion;
    

    @ManyToMany(mappedBy = "riesgos")
    private List<Proyecto> proyectos;
    
    @ManyToMany(mappedBy = "riesgos")
	private List<ActivoFisico> activosFisicos;
 

    public List<Proyecto> getProyectos() {
		return proyectos;
	}
    
    public Proyecto getProyecto(){
		return proyectos.get(0);
	}


	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}


	public LocalDate getFechaAprobacion() {
		return fechaAprobacion;
	}


	public void setFechaAprobacion(LocalDate fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}


	public User getUsuarioCreador() {
		return usuarioCreador;
	}


	public void setUsuarioCreador(User usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}


	public User getUsuarioAuditor() {
		return usuarioAuditor;
	}


	public void setUsuarioAuditor(User usuarioAuditor) {
		this.usuarioAuditor = usuarioAuditor;
	}


	public Impacto getImpacto() {
		return impacto;
	}


	public void setImpacto(Impacto impacto) {
		this.impacto = impacto;
	}


	public Riesgo() {
    }
    
    
    public Riesgo(Long id, LocalDate fechaAnalisis, String codigoRiesgo, String descripcion, String codigoFormulario,
    		Amenaza amenaza,String responsable, Boolean afectaConfidencialidad, Boolean afectaIntegridad, Boolean afectaDisponibilidad,
			ProbalidadOcurrencia probabilidadOcurrencia, RiesgoInherenteValor riesgoInherenteValor, Salvaguarda salvaguarda,
			RiesgoResidualValor riesgoResidualValor, Impacto impacto) {
		super();
		this.id = id;
		this.fechaAnalisis = fechaAnalisis;
		this.codigoRiesgo = codigoRiesgo;
		this.descripcion = descripcion;
		this.codigoFormulario = codigoFormulario;
		this.responsable = responsable;
		this.afectaConfidencialidad = afectaConfidencialidad;
		this.afectaIntegridad = afectaIntegridad;
		this.afectaDisponibilidad = afectaDisponibilidad;
		this.probabilidadOcurrencia = probabilidadOcurrencia;
		this.riesgoInherenteValor = riesgoInherenteValor;
		this.salvaguarda = salvaguarda;
		this.riesgoResidualValor = riesgoResidualValor;
		this.amenaza = amenaza;
		this.impacto = impacto;
	}






	public Amenaza getAmenaza() {
		return amenaza;
	}


	public void setAmenaza(Amenaza amenaza) {
		this.amenaza = amenaza;
	}


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public LocalDate getFechaAnalisis() {
		return fechaAnalisis;
	}




	public void setFechaAnalisis(LocalDate fechaAnalisis) {
		this.fechaAnalisis = fechaAnalisis;
	}


	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




	public String getCodigoFormulario() {
		return codigoFormulario;
	}




	public void setCodigoFormulario(String codigoFormulario) {
		this.codigoFormulario = codigoFormulario;
	}




	public String getResponsable() {
		return responsable;
	}




	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}




	public Boolean getAfectaConfidencialidad() {
		return afectaConfidencialidad;
	}
	
	public String getAfectaConfidencialidadAsString() {
		return afectaConfidencialidad?"SI":"NO";
	}

	public void setAfectaConfidencialidad(Boolean afectaConfidencialidad) {
		this.afectaConfidencialidad = afectaConfidencialidad;
	}


	public Boolean getAfectaIntegridad() {
		return afectaIntegridad;
	}
	
	public String getAfectaIntegridadAsString() {
		return afectaIntegridad?"SI":"NO";
	}


	public void setAfectaIntegridad(Boolean afectaIntegridad) {
		this.afectaIntegridad = afectaIntegridad;
	}

	public Boolean getAfectaDisponibilidad() {
		return afectaDisponibilidad;
	}

	public String getAfectaDisponibilidadAsString() {
		return afectaDisponibilidad?"SI":"NO";
	}




	public void setAfectaDisponibilidad(Boolean afectaDisponibilidad) {
		this.afectaDisponibilidad = afectaDisponibilidad;
	}




	public ProbalidadOcurrencia getProbabilidadOcurrencia() {
		return probabilidadOcurrencia;
	}




	public void setProbabilidadOcurrencia(ProbalidadOcurrencia probabilidadOcurrencia) {
		this.probabilidadOcurrencia = probabilidadOcurrencia;
	}
	
	
	public RiesgoInherenteValor getRiesgoInherenteValor() {
		return riesgoInherenteValor;
	}


	public void setRiesgoInherenteValor(RiesgoInherenteValor riesgoInherenteValor) {
		this.riesgoInherenteValor = riesgoInherenteValor;
	}


	public Salvaguarda getSalvaguarda() {
		return salvaguarda;
	}




	public void setSalvaguarda(Salvaguarda salvaguarda) {
		this.salvaguarda = salvaguarda;
	}




	public RiesgoResidualValor getRiesgoResidualValor() {
		return riesgoResidualValor;
	}


	public void setRiesgoResidualValor(RiesgoResidualValor riesgoResidualValor) {
		this.riesgoResidualValor = riesgoResidualValor;
	}

	public String getCodigoRiesgo() {
		return codigoRiesgo;
	}


	public void setCodigoRiesgo(String codigoRiesgo) {
		this.codigoRiesgo = codigoRiesgo;
	}


	@Override
    public String toString() {
        return "Riesgo{" + "id=" + id + ", descripcion=" + descripcion + ", codigo=" + codigoRiesgo + ", codigo de Formulario=" + codigoFormulario + ",  Amenaza=" + amenaza + ", responsable=" + responsable +  "}";
    }

	public List<ActivoFisico> getActivosFisicos() {
		return activosFisicos;
	}

	public void setActivosFisicos(List<ActivoFisico> activosFisicos) {
		this.activosFisicos = activosFisicos;
	}
	
	public String getAmenazaStr() {
		return   amenaza.getOrigen().getOrigen()+ " "+ amenaza.getTipo()+", Riesgo Residual: "+this.getRiesgoResidualValor().getValor() ;
	}
    
	public String getDescripcionLarga() {
		return "Riesgo NRO: "+this.getId()+", Amenaza: "+getAmenazaStr();
	}
	
	public String getDescripcionCorta() {
		return "Riesgo NRO: "+this.getId()+", Amenaza: "+ amenaza.getOrigen().getOrigen()+ " "+ amenaza.getTipo();
	}

	public int compareToAmenazaStr(Riesgo riesgo) {
		return this.getAmenazaStr().compareTo(riesgo.getAmenazaStr());
	}
}

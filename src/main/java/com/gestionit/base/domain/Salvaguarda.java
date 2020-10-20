package com.gestionit.base.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Audited
public class Salvaguarda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1716499655554439196L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")    
    private Long id;
	
	private String descripcion;
	
	@OneToOne
    @JoinColumn(name="fk_tipo_salvaguarda")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private SalvaguardaTipo tipo;
	
	@OneToOne
	@JoinColumn(name="fk_impacto")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Impacto impactoFinal;

	@OneToOne
	@JoinColumn(name="fk_prob_ocur")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ProbalidadOcurrencia probabilidadFinal;

	public Impacto getImpactoFinal() {
		return impactoFinal;
	}

	public void setImpactoFinal(Impacto impactoFinal) {
		this.impactoFinal = impactoFinal;
	}

	public ProbalidadOcurrencia getProbabilidadFinal() {
		return probabilidadFinal;
	}

	public void setProbabilidadFinal(ProbalidadOcurrencia probabilidadFinal) {
		this.probabilidadFinal = probabilidadFinal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public SalvaguardaTipo getTipo() {
		return tipo;
	}

	public void setTipo(SalvaguardaTipo tipo) {
		this.tipo = tipo;
	}

	
	public Integer getValor() {
		return tipo==null?0:tipo.getValor();
	}
	
	@Override
    public String toString() {
        return "Salvaguarda{" + "id=" + id + ", descripcion=" + descripcion + ", menor Impacto=" + impactoFinal + ", menor Probabilidad=" + probabilidadFinal + '}';
    }
}

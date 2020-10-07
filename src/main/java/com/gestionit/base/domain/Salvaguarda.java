package com.gestionit.base.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
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
	private SalvaguardaTipo tipo;
	
	@Column(name = "menor_impacto")
	private Integer menorImpacto;
	
	@Column(name = "menor_probabilidad")
	private Integer menorProbabilidad;

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

	

	public Integer getMenorImpacto() {
		return menorImpacto;
	}

	public SalvaguardaTipo getTipo() {
		return tipo;
	}

	public void setTipo(SalvaguardaTipo tipo) {
		this.tipo = tipo;
	}

	public void setMenorImpacto(Integer menorImpacto) {
		this.menorImpacto = menorImpacto;
	}

	public Integer getMenorProbabilidad() {
		return menorProbabilidad;
	}

	public void setMenorProbabilidad(Integer menorProbabilidad) {
		this.menorProbabilidad = menorProbabilidad;
	}
	
	public Integer getValor() {
		return tipo==null?0:tipo.getValor();
	}
	
	@Override
    public String toString() {
        return "Salvaguarda{" + "id=" + id + ", descripcion=" + descripcion + ", menor Impacto=" + menorImpacto + ", menor Probabilidad=" + menorProbabilidad + '}';
    }
}

package com.gestionit.base.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class SalvaguardaTipo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1716499655554439196L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")    
    private Long id;
	
	private String descripcion;
	
	private Integer valor;
	

	public SalvaguardaTipo(Integer valor, String descripcion) {
		super();
		this.descripcion = descripcion;
		this.valor = valor;
				
	}

	public SalvaguardaTipo() {
		super();
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
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

	
	@Override
    public String toString() {
        return "Salvaguarda{" + "id=" + id + ", descripcion=" + descripcion +  ", menor Probabilidad=" + '}';
    }
}

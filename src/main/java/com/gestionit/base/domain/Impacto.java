package com.gestionit.base.domain;

import javax.persistence.Entity;


@Entity
public class Impacto extends ItemRiesgo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8259750273688221518L;
	
	private String valorMonetario;

	

	public Impacto() {
		super();
	}

	public Impacto(Integer valor, String calificacion) {
		super(valor, calificacion);
	}
	
	public String getValorMonetario() {
		return valorMonetario==null?"":valorMonetario;
	}

	public void setValorMonetario(String valorMonetario) {
		this.valorMonetario = valorMonetario;
	}
	
	public String getImpactoMonetario() {
		return this.getCalificacion()+" "+getValorMonetario();
	}

}

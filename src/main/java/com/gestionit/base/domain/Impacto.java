package com.gestionit.base.domain;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
public class Impacto extends ItemRiesgo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8259750273688221518L;

	public Impacto() {
		super();
	}

	public Impacto(Integer valor, String calificacion) {
		super(valor, calificacion);
	}

}

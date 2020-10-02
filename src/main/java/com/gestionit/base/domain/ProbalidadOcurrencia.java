package com.gestionit.base.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="probalidad_ocurrencia")
public class ProbalidadOcurrencia extends ItemRiesgo {

	public ProbalidadOcurrencia() {
		super();

	}

	public ProbalidadOcurrencia(Integer valor, String calificacion) {
		super(valor, calificacion);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8818902459869876223L;

}

package com.gestionit.base.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="riesgo_residual")
public class RiesgoResidual extends ItemRiesgo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8818902459869876223L;

	public RiesgoResidual() {
		super();
		
	}

	public RiesgoResidual(Integer valor, String calificacion) {
		super(valor, calificacion);

	}
	

}

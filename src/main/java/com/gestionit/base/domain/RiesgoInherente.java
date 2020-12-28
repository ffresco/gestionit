package com.gestionit.base.domain;


import javax.persistence.Entity;
import javax.persistence.Table;




@Entity
@Table(name="riesgo_inherente")
public class RiesgoInherente extends ItemRiesgo  {
	

	public RiesgoInherente() {
		super();
	}

	public RiesgoInherente(Integer valor, String calificacion) {
		super(valor, calificacion);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8818902459869876223L;


}

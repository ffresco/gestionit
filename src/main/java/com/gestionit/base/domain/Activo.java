package com.gestionit.base.domain;

import javax.persistence.Entity;

@Entity
public class Activo extends RiesgoState {

	@Override
	public String getEstado() {
		return "Activo";
	}

}

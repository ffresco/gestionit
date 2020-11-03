package com.gestionit.base.domain;

import javax.persistence.Entity;

@Entity
public class Cancelado extends RiesgoState {

	@Override
	public String getEstado() {
		return "Cancelado";
	}

}

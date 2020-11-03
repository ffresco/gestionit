package com.gestionit.base.domain;

import javax.persistence.Entity;

@Entity
public class Solucionado extends RiesgoState {

	@Override
	public String getEstado() {
		return "Solucionado";
	}

}

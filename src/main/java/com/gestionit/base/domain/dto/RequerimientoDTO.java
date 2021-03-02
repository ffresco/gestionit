package com.gestionit.base.domain.dto;

import java.util.List;

import com.gestionit.base.domain.Requerimiento;
import com.gestionit.base.domain.RequerimientoResultado;

public class RequerimientoDTO {

	
private Requerimiento requerimiento;
private List<RequerimientoResultado> nuevosResultados;


public RequerimientoDTO(Requerimiento requerimiento) {
	super();
	this.setRequerimiento(requerimiento);
}

public RequerimientoDTO() {
	super();
	// TODO Auto-generated constructor stub
}

public Requerimiento getRequerimiento() {
	return requerimiento;
}

public void setRequerimiento(Requerimiento requerimiento) {
	this.requerimiento = requerimiento;
}


public List<RequerimientoResultado> getNuevosResultados() {
	return nuevosResultados;
}

public void setNuevosResultados(List<RequerimientoResultado> nuevoResultados) {
	this.nuevosResultados = nuevoResultados;
}

    
    
}

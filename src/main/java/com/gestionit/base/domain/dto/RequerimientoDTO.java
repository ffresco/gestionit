package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Requerimiento;
import com.gestionit.base.domain.RequerimientoResultado;

public class RequerimientoDTO {

	
private Requerimiento requerimiento;
private RequerimientoResultado nuevoResultado;

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

public RequerimientoResultado getNuevoResultado() {
	return nuevoResultado;
}

public void setNuevoResultado(RequerimientoResultado nuevoResultado) {
	this.nuevoResultado = nuevoResultado;
}

    
    
}

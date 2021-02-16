package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Requerimiento;

public class RequerimientoDTO {

	
private Requerimiento requerimiento;

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

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.ActivoFisico;

/**
 *
 * @author cbova
 */
public class ActivoFisicoDTO {
	

	private ActivoFisico activoFisico;
	private String fechaClasificacion;

	public ActivoFisicoDTO() {
		
	}
    

	public ActivoFisico getActivoFisico() {
		return activoFisico;
	}


	public void setActivoFisico(ActivoFisico activoFisico) {
		this.activoFisico = activoFisico;
	}



	@Override
    public String toString() {
        return "ActivoFisicoDTO{ Activo Fisico"  + activoFisico +"}";
    }


	public void setFechaClasificacion(String fechaClasificacion) {
		this.fechaClasificacion = fechaClasificacion;
		
	}


	public String getFechaClasificacion() {
		return fechaClasificacion;
	}

	

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

/**
 *
 * @author cbova
 */
public class RiesgoSearchDTO {
    
    private String fechaAnalisis;
    private String responsable;
    private String codigoFormulario;
    private String origenAmenaza;

    public RiesgoSearchDTO() {
    }

   
    


	public String getFechaAnalisis() {
		return fechaAnalisis;
	}


	public void setFechaAnalisis(String fechaAnalisis) {
		this.fechaAnalisis = fechaAnalisis;
	}


	public String getResponsable() {
		return responsable;
	}




	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}




	public String getCodigoFormulario() {
		return codigoFormulario;
	}




	public void setCodigoFormulario(String codigoFormulario) {
		this.codigoFormulario = codigoFormulario;
	}




	public String getOrigenAmenaza() {
		return origenAmenaza;
	}




	public void setOrigenAmenaza(String origenAmenaza) {
		this.origenAmenaza = origenAmenaza;
	}




	@Override
    public String toString() {
        return "RiesgoSearchDTO{" + "fecha=" + fechaAnalisis + ", origenAmenaza=" + origenAmenaza + ", codigo Formulario=" + codigoFormulario + '}';
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.User;

/**
 *
 * @author cbova
 */
public class RiesgoSearchDTO {
    
    private String fechaAnalisis;
    private String responsable;
    private String codigoFormulario;
    private String origenAmenaza;
	private User currentUser;
	private String codigoRiesgo;
	private boolean onlyOneUser;
	private Long id;






	public RiesgoSearchDTO() {
    }
   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigoRiesgo() {
		return codigoRiesgo;
	}

	public void setCodigoRiesgo(String codigoRiesgo) {
		this.codigoRiesgo = codigoRiesgo;
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





	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		
	}
    
	public User getCurrentUser() {
		return this.currentUser;
	}


	public void setOnlyOneUser(boolean onlyOneUser) {
		this.onlyOneUser = onlyOneUser;
	}


	public boolean isOnlyOneUser() {
		return onlyOneUser;
	}
	
    
    
}

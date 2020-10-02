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
    
    private String fecha;
    private String origen;
    private String codigoFormulario;

    public RiesgoSearchDTO() {
    }

   
    public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getOrigen() {
		return origen;
	}


	public void setOrigen(String origen) {
		this.origen = origen;
	}


	public String getCodigoFormulario() {
		return codigoFormulario;
	}


	public void setCodigoFormulario(String codigoFormulario) {
		this.codigoFormulario = codigoFormulario;
	}


	@Override
    public String toString() {
        return "RiesgoSearchDTO{" + "fecha=" + fecha + ", origen=" + origen + ", codigo Formulario=" + codigoFormulario + '}';
    }
    
    
    
}

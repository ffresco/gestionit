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
public class ActivoFisicoSearchDTO extends SearchDTO<ActivoFisico> {
    
    private String nombre;
    private String tipo;
    private String propietario;
    private String ubicacion;
    private String codigo;
	



	public ActivoFisicoSearchDTO() {
    }
 
	public String getNombre() {
		return nombre;
	}




	public String getTipo() {
		return tipo;
	}




	public String getPropietario() {
		return propietario;
	}




	public String getUbicacion() {
		return ubicacion;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}




	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}



	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	@Override
	public String toString() {
		return "ActivoFisicoSearchDTO{" + "nombre=" + nombre + ", tipo=" + tipo  + '}';
	}






    
}

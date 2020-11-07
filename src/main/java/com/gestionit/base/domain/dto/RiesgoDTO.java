/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.gestionit.base.domain.Amenaza;
import com.gestionit.base.domain.OrigenAmenaza;
import com.gestionit.base.domain.Proyecto;
import com.gestionit.base.domain.Riesgo;

/**
 *
 * @author cbova
 */
public class RiesgoDTO {
	

	private Riesgo riesgo;
	
	private String fechaAnalisis;
	
	private boolean modificable;
	
    private boolean procesado;
    
    private boolean readOnly;
    

    private boolean edit;
    
    private List<Amenaza> amenazas;
    
    private OrigenAmenaza origenAmenaza;
    
    private boolean aprobacion; //indica si se habilita el boton de aprobacion.
    
    private Proyecto proyecto;
    
    private Proyecto proyectoCopy; //guardo una copia del proyecto original
    




	public RiesgoDTO() {
		
	}
    


	public Proyecto getProyectoCopy() {
		return proyectoCopy;
	}



	public void setProyectoCopy(Proyecto proyectoCopy) {
		this.proyectoCopy = proyectoCopy;
	}


  
	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
		this.proyectoCopy = proyecto;
	}


	public String getFirstDay() {
		return LocalDateTime.now().minusMonths(2).format(DateTimeFormatter.ISO_LOCAL_DATE);
	}


	public String getToday() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
	}



	public boolean isAprobacion() {
		return aprobacion;
	}


	public void setAprobacion(boolean aprobacion) {
		this.aprobacion = aprobacion;
	}


	public List<Amenaza> getAmenazas() {
		return amenazas;
	}


	public void setAmenazas(List<Amenaza> amenazas) {
		this.amenazas = amenazas;
	}


	public RiesgoDTO(Riesgo riesgoAEditar) {
		this.riesgo = riesgoAEditar;
	}


	public boolean isEdit() {
		return edit;
	}


	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	
    public String getFechaAnalisis() {
		return fechaAnalisis;
	}


	public void setFechaAnalisis(String fechaAnalisis) {
		this.fechaAnalisis = fechaAnalisis;
	}


	public Riesgo getRiesgo() {
		return riesgo;
	}


	public void setRiesgo(Riesgo riesgo) {
		this.riesgo = riesgo;
	}


	public boolean isModificable() {
		return modificable;
	}


	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}


	public boolean isProcesado() {
		return procesado;
	}


	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}


	public boolean isReadOnly() {
		return readOnly;
	}


	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}



	@Override
    public String toString() {
        return "RiesgoDTO{ Riesgo"  + riesgo +"}";
    }


	public void configEditScreen() {
		 this.edit = true;
		 this.modificable = true;
		 this.procesado = false;
		
	}

	 public void configAltaScreen(){
	        this.edit=false;
	        this.modificable = true;
		    this.procesado = false;
	    }


	public OrigenAmenaza getOrigenAmenaza() {

		return origenAmenaza;
	}


	public void setOrigenAmenaza(OrigenAmenaza origenAmenaza) {
		this.origenAmenaza = origenAmenaza;
	}

	
    
    
    
}

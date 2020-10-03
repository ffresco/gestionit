/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;


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
    
    
  
	public RiesgoDTO(Riesgo riesgoAEditar) {
		this.riesgo = riesgoAEditar;
	}


	public RiesgoDTO() {
		
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

	
    
    
    
}

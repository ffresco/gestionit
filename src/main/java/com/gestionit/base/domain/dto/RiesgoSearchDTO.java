/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.User;

/**
 *
 * @author cbova
 */
public class RiesgoSearchDTO extends SearchDTO<Riesgo>{
    
    private String fechaAnalisis;
    private String responsable;
    private String codigoFormulario;
    private String origenAmenaza;
	private User currentUser;
	private String codigoRiesgo;
	private boolean onlyOneUser;
	private Long id;
	private String filtro;
	private Map<Integer, Map<Integer, Long>> matrizDeRiesgo;
	private Map<String, Integer> page = new HashMap<>();
	private List<Riesgo> riesgos;


	public RiesgoSearchDTO(){
    }
   
	public Map<Integer, Map<Integer, Long>> getMatrizDeRiesgo() {
		return matrizDeRiesgo;
	}



	public void setMatrizDeRiesgo(Map<Integer, Map<Integer, Long>> matrizDeRiesgo) {
		this.matrizDeRiesgo = matrizDeRiesgo;
	}

	
	public String getFiltro() {
		return filtro;
	}



	public void setFiltro(String filtro) {
		this.filtro = filtro;
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

	public Map<String, Integer> getPage() {
		return page;
	}

	public void setPage(Map<String, Integer> page) {
		this.page = page;
	}

	public List<Riesgo> getRiesgos() {
		return riesgos;
	}

	public void setRiesgos(List<Riesgo> riesgos) {
		this.riesgos = riesgos;
	}
	
    
    
}

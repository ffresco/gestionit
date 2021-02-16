package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Requerimiento;


public class RequerimientoSearchDTO extends SearchDTO<Requerimiento> {
	
	private String solicitante;
	private String categoria;
	private String area;
	
	
	public String getSolicitante() {
		return solicitante;
	}
	public String getCategoria() {
		return categoria;
	}
	public String getArea() {
		return area;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	

}

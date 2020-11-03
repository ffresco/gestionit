package com.gestionit.base.domain.dto;

public class ProyectoSearchDTO {
	
	private Long id;
	private String objetivo;
	private String alcance;
	private String costoTotal;
	
	public String getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(String costoTotal) {
		this.costoTotal = costoTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	

}

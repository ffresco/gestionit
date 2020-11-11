package com.gestionit.base.domain.dto;




public class EventoSearchDTO {

	public EventoSearchDTO(Long riesgoID) {
		super();
		this.riesgoID = riesgoID;
	}

	private Long riesgoID;
	
	public EventoSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String descripcion;

	public Long getRiesgoID() {
		return riesgoID;
	}

	public void setRiesgoID(Long riesgoID) {
		this.riesgoID = riesgoID;
	}

	public String getDescripcion() {

		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}

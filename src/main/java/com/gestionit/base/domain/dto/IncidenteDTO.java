package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Incidente;

/**
 *
 * @author Cbova
 */

public class IncidenteDTO {

	private Incidente incidente;

	private String fecha;
	
	private String responsableResolucion;
	
	private String solucion;

	public String getSolucion() {
		return solucion;
	}

	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}

	public String getResponsableResolucion() {
		return responsableResolucion;
	}

	public void setResponsableResolucion(String responsableResolucion) {
		this.responsableResolucion = responsableResolucion;
	}

	public Incidente getIncidente() {
		return incidente;
	}

	public void setIncidente(Incidente incidente) {
		this.incidente = incidente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "IncidenteDTO{" + "incidente=" + incidente + '}';
	}	

}

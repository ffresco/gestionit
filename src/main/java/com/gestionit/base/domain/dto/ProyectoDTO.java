package com.gestionit.base.domain.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.gestionit.base.domain.Proyecto;

public class ProyectoDTO {
	
	private Proyecto proyecto;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	
	public ProyectoDTO() {
		super();
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ProyectoDTO(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
		
	}
	
	public String getToday() {
		if(fechaInicio!=null) {
			return fechaInicio;
		}else {
			return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		}
		
	}


}

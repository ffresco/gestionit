/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Evento;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Cbova
 */
public class EventoDTO {

    private Evento evento;
    private String fecha;

    public EventoDTO() {
    }

    public EventoDTO(Evento evento) {
        this.evento = evento;
        this.fecha = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
       
    }

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public String getToday() {
		return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

   

   
}

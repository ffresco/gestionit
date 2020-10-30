package com.gestionit.base.domain.dto;

import java.util.Map;

public class MapaDeRiesgoDTO {
	
	private Map<Integer, Map<Integer, Long>> mapaDeRiesgo;
	
	public MapaDeRiesgoDTO(Map<Integer, Map<Integer, Long>> mapaDeRiesgo) {
		super();
		this.mapaDeRiesgo = mapaDeRiesgo;
	}

	public Map<Integer, Map<Integer, Long>> getMapaDeRiesgo() {
		return mapaDeRiesgo;
	}

	public void setMapaDeRiesgo(Map<Integer, Map<Integer, Long>> mapaDeRiesgo) {
		this.mapaDeRiesgo = mapaDeRiesgo;
	}
	
	public String getProbabilidad(Integer index) {
		String result="";
		switch (index) {
		case 1:
			result="IMPROBABLE";
			break;
		case 2:
			result="BAJA";
			break;
		case 3:
			result="MEDIA";
			break;
		case 4:
			result="ALTA";
			break;

		default:
			result="MUY ALTA";
			break;
		}
		
		return result;
	}

	public String getColor(Integer probabilidad, Integer impacto) {
		if(impacto==1) {
			return "#9CEF72";
		}else if (probabilidad == 1 ) {
			return "#9CEF72";
		} else if (impacto==2 && probabilidad == 2 ) {
			return "#9CEF72";
		} else if (impacto==2 && probabilidad >= 3 ) {
			return "#E9E08B";
		} else  if (impacto==3 && (probabilidad == 3 || probabilidad == 2) ) {
			return "#E9E08B";
		} else  if (impacto==4 && probabilidad == 2 ) {
			return "#E9E08B";
		} else  if (impacto==5 && probabilidad == 2 ) {
			return "#E9E08B";
		} else  if (impacto==3 && probabilidad >= 4 ) {
			return "#FFCCCC";
		} else  if (impacto==4 && probabilidad == 3 ) {
			return "FFCCCC";
		} else  if (impacto==5 && probabilidad == 3 ) {
			return "#FFCCCC";
		} else  if (impacto==4 && probabilidad > 3 ) {
			return "#B55757";
		} else  if (impacto==5 && probabilidad == 4 ) {
			return "#B55757";
		} else {
			return "#F90707";
		}
	}
	
	}


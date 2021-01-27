package com.gestionit.base.domain.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gestionit.base.domain.Riesgo;

public class MapaDeRiesgoDTO {
	
	private Map<Integer, Map<Integer, List<Riesgo>>> mapaDeRiesgo;
	
	
	public MapaDeRiesgoDTO(Map<Integer, Map<Integer, List<Riesgo>>> mapaDeRiesgo) {
		super();
		this.mapaDeRiesgo = mapaDeRiesgo;
	}

	public Map<Integer, Map<Integer, List<Riesgo>>> getMapaDeRiesgo() {
		return mapaDeRiesgo;
	}

	public void setMapaDeRiesgo(Map<Integer, Map<Integer, List<Riesgo>>> mapaDeRiesgo) {
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
	
	public String getDivId(Integer key, List<Riesgo> riesgos){

		return riesgos.isEmpty()?String.valueOf(Math.random()):getDivIdStr(key, riesgos);
	}
	
	public String getDivFunction(Integer key, List<Riesgo> riesgos){
		return riesgos.isEmpty()?"hideElements();":"hideElements();document.getElementById('"+ getDivIdStr(key, riesgos)+"').style.display = 'block';";
	}
	
private String getDivIdStr(Integer key, List<Riesgo> riesgos) {
	Integer id = key;
	for (Riesgo riesgo : riesgos) {
		id = id + riesgo.getId().intValue();
	}
	return id.toString();
}
	}


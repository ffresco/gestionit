package com.gestionit.base.service;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.Incidente;
import com.gestionit.base.domain.dto.IncidenteSearchDTO;
import com.gestionit.base.repository.InicidenteRepository;

/**
*
* @author Cbova
*/
@Service
public class IncidenteService {

	 private final InicidenteRepository incidenteRepository;

	public IncidenteService(InicidenteRepository incidenteRepository) {
		super();
		this.incidenteRepository = incidenteRepository;
	}
	 
	 public Incidente getById(Long id){
	        return incidenteRepository.findOne(id);
	    }

	public List<Incidente> getAllContaining(IncidenteSearchDTO incdto) {
		String responsable = getResponsableIncParam(incdto);
        String solucion = getSolucionParam(incdto);
        System.out.println("--"+ responsable+solucion);
        return (List<Incidente>) incidenteRepository.findByValoresLike(responsable,solucion, new Sort(Sort.Direction.DESC,"fechaHora"));
	}

	public Incidente saveOrUpdate(Incidente inc) {
		return incidenteRepository.save(inc);
	}

	public void delete(Incidente inc) {
		incidenteRepository.delete(inc);
		
	}
	

	private String getSolucionParam(IncidenteSearchDTO incdto) {
		return incdto.getSolucion()==null?"":incdto.getClasificacion();
	}

	private String getResponsableIncParam(IncidenteSearchDTO incdto) {
		return incdto.getResponsableResolucion()==null?"":incdto.getResponsableResolucion();
	}

	 
}

package com.gestionit.base.service;


import java.util.List;

import org.codehaus.groovy.transform.trait.Traits.TraitBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestionit.base.domain.Evento;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.EventoSearchDTO;
import com.gestionit.base.repository.EventoRepository;

/**
*
* @author Cbova
*/
@Service
public class EventoService {
	
	private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

	public EventoRepository getEventoRepository() {
		return eventoRepository;
	}

	public Iterable<Evento> findAll() {
		
		return eventoRepository.findAll();
	}

	public Evento save(Evento evento) {
		return eventoRepository.save(evento);
	}

	public Evento getById(Long id) {
		return eventoRepository.findOne(id);
	}

	public void delete(Evento evento) {
		 eventoRepository.delete(evento);
		
	}
    @Transactional 
	public void delete(Riesgo riesgo) {
		List<Evento> eventos = eventoRepository.findByRiesgoId(riesgo.getId());
		for (Evento evento : eventos) {
			this.delete(evento);
		}
	}


	public Object findAllContaining(EventoSearchDTO searchDTO) {
		
		return eventoRepository.findByValoresLike(searchDTO.getRiesgoID(), searchDTO.getDescripcion().toUpperCase());
	}

}

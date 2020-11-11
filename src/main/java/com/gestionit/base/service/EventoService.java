package com.gestionit.base.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestionit.base.domain.Evento;
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

	public void delete(Evento id) {
		 eventoRepository.delete(id);
		
	}

	public Object findAllContaining(EventoSearchDTO searchDTO) {
		
		return eventoRepository.findByValoresLike(searchDTO.getRiesgoID(), searchDTO.getDescripcion().toUpperCase());
	}

}

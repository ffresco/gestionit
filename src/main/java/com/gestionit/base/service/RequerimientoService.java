package com.gestionit.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gestionit.base.domain.Requerimiento;
import com.gestionit.base.domain.RequerimientoResultado;
import com.gestionit.base.domain.dto.RequerimientoSearchDTO;
import com.gestionit.base.repository.RequerimientoRepository;
import com.gestionit.base.repository.RequerimientoResultadoRepository;

@Service
public class RequerimientoService implements BasicService<Requerimiento>{
	

	private RequerimientoRepository requerimientoRepository;
	
	private RequerimientoResultadoRepository requerimientoResultadoRepository;
	
	@Autowired
    public RequerimientoService(RequerimientoRepository requerimientoRepository, RequerimientoResultadoRepository requerimientoResultadoRepository) {
        this.requerimientoRepository = requerimientoRepository;
        this.requerimientoResultadoRepository = requerimientoResultadoRepository;
       
        
    }


	public List<Requerimiento> findAll() {
		
		return (List<Requerimiento>) requerimientoRepository.findAll();
	}


	public List<Requerimiento> findAllContaining(RequerimientoSearchDTO searchDTO) {
		
		return requerimientoRepository.findAllContaining(searchDTO.getSolicitante(), searchDTO.getCategoria(), searchDTO.getArea());
	}
	
	
public Page<Requerimiento> findAllContaining(RequerimientoSearchDTO searchDTO, Pageable pageable) {
		
		return requerimientoRepository.findAllContaining(searchDTO.getSolicitante(), searchDTO.getCategoria(), searchDTO.getArea(), pageable);
	}

	@Override
	@Transactional
	public  Requerimiento saveOrUpdate(Requerimiento requerimiento) {
		return requerimientoRepository.save(requerimiento);
	}


	@Override
	public void delete(Requerimiento entity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Requerimiento getById(Long id) {
		
		return requerimientoRepository.findOne(id);
	}


	public Page<Requerimiento> getPaginatedRequerimientos(int pageNumber, int pageSize) {
		final Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
        return requerimientoRepository.findAll(pageable);
	}


	public Page<Requerimiento> getPaginatedRequerimientos(int pageNumber, int pageSize,
			RequerimientoSearchDTO searchDTO) {
		 final Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
	        return this.findAllContaining(searchDTO, pageable);
	}

	@Transactional
	public Requerimiento saveOrUpdate(Requerimiento requerimiento, List<RequerimientoResultado> resultadosToRetain) {
		
		requerimiento.getResultados().clear();
		requerimiento.getResultados().addAll(resultadosToRetain);
		return this.saveOrUpdate(requerimiento);
	}



}

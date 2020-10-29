/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.MyRevision;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.RiesgoAuditDTO;
import com.gestionit.base.domain.dto.RiesgoSearchDTO;
import com.gestionit.base.repository.RiesgoRepository;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fafre
 */
@Service
public class RiesgoService implements BasicService<Riesgo>{
    
    private RiesgoRepository riesgoRepo;
    
    private AuditReader reader;


    @Autowired
    public RiesgoService(RiesgoRepository riesgoRepo, AuditReader reader) {
        this.riesgoRepo = riesgoRepo;
        this.reader = reader;

        
    }

    @Override
    public void delete(Riesgo entity) {
    	riesgoRepo.delete(entity);
    }

    @Override
    public Riesgo getById(Long id) {
        return riesgoRepo.findOne(id);
    }

    @Transactional
    @Override
    public Riesgo saveOrUpdate(Riesgo entity) {

        Riesgo riesgo = riesgoRepo.save(entity);
        System.out.println("Riesgo Gravado" + riesgo);
        return riesgo;
    }

    @Override
    public List<Riesgo> findAll() {
        return (List<Riesgo>) riesgoRepo.findAll();
    }   
 

    public List<Riesgo> findAllContaining(RiesgoSearchDTO searchDTO) {
    	String filtro = searchDTO.getFiltro();
    	if(filtro.equals("Todos")) {
    		return (List<Riesgo>) riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable());
    	}else {
    		return (List<Riesgo>) riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable(),filtro.equals("Aprobados"));
    	}
        
    }   
    
    
    public List<RiesgoAuditDTO> findAllDeleted() throws IllegalAccessException, InvocationTargetException {
    	List<RiesgoAuditDTO> result = new ArrayList<RiesgoAuditDTO>(); 
    	 AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    	            Riesgo.class, false, true);
    	    query.add(AuditEntity.revisionType().eq(RevisionType.DEL));

    	    List<Object[]> results = query.getResultList();
    	    for (Object[] obj : results) {
    	        Riesgo riesgo = (Riesgo) obj[0];
    	        MyRevision dre = (MyRevision) obj[1];
    	        RevisionType revType = (RevisionType) obj[2];
    	        
    	        
    	        AuditQuery queryAux = reader.createQuery().forRevisionsOfEntity(
                        Riesgo.class, false, true);
                queryAux.add(AuditEntity.revisionType().ne(RevisionType.DEL));
                queryAux.addProjection(AuditEntity.revisionNumber().max());
                queryAux.add(AuditEntity.id().eq(riesgo.getId()));
                     
                Number idUltimaRevision = (Number) queryAux.getSingleResult();
                
                queryAux = reader.createQuery().forRevisionsOfEntity(
            			Riesgo.class, false, false);
                queryAux.add(AuditEntity.id().eq(riesgo.getId()));
                queryAux.add(AuditEntity.revisionNumber().eq(idUltimaRevision));
            	results = queryAux.getResultList();
           	    Riesgo riesgo_ = (Riesgo) results.get(0)[0];
                RiesgoAuditDTO auditDTO = new RiesgoAuditDTO(riesgo_, dre, revType);
        	    result.add(auditDTO);
        	   System.out.println("Riesgo borrado " + riesgo.getId() + " en fecha "
        	                + dre.getRevisionDate());
           	    }
                
       	    return result;  
              
    	    }
    	   
        
    	    
 
    
    public RiesgoAuditDTO getLastAuditedRecord(Long id) {
    	AuditQuery queryAux = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, true);
    	queryAux.add(AuditEntity.revisionType().ne(RevisionType.DEL));
    	queryAux.addProjection(AuditEntity.revisionNumber().max());
    	queryAux.add(AuditEntity.id().eq(id));

    	Number idLastRevision = (Number) queryAux.getSingleResult();
    	Riesgo riesgo = reader.find(Riesgo.class,
    			id, idLastRevision);

    	return new RiesgoAuditDTO(riesgo);
    }


    public Date getCreateDate(Long id) {
    	AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, true);
    	query.add(AuditEntity.revisionType().eq(RevisionType.ADD));
    	query.addProjection(AuditEntity.revisionNumber().min());
    	query.add(AuditEntity.id().eq(id));

    	Number revision = (Number) query.getSingleResult();

    	return reader.getRevisionDate(revision);
    }
    
    public List<RiesgoAuditDTO> getAllAuditForId(Long id) {
    	List<RiesgoAuditDTO> result = new ArrayList<RiesgoAuditDTO>(); 
    	AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, false);
    	query.add(AuditEntity.id().eq(id));
    	
    	   List<Object[]> results = query.getResultList();
   	    for (Object[] obj : results) {
   	        Riesgo riesgo = (Riesgo) obj[0];
   	        MyRevision dre = (MyRevision) obj[1];
   	        RevisionType revType = (RevisionType) obj[2];
   	        RiesgoAuditDTO auditDTO = new RiesgoAuditDTO(riesgo, dre, revType);
   	        result.add(auditDTO);
   	       
   	    }
   	    return result;
    }
    
   
    
}

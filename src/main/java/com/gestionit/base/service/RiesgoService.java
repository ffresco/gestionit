/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.Riesgo;

import com.gestionit.base.repository.RiesgoRepository;


import java.util.List;
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


    @Autowired
    public RiesgoService(RiesgoRepository riesgoRepo) {
        this.riesgoRepo = riesgoRepo;

        
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
 
    
    
    
}

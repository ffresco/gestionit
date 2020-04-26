/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.TopeCompra;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fafre
 */
public interface TopesRepo extends CrudRepository<TopeCompra, Long> {
    
    public List<TopeCompra> findAllByOrderByFechaAltaDesc();
    
}

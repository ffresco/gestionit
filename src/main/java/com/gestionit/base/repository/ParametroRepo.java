/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.Parametro;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fafre
 */
public interface ParametroRepo extends CrudRepository<Parametro,Long> {
    
    public Parametro findByValor(String valor);
    
    public List<Parametro> findByTipo(String tipo);
    

    
    
}

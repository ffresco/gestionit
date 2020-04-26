/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;


import com.gestionit.base.domain.AcumuladoCliente;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fafre
 */
public interface AcumuladoClienteRepo extends CrudRepository<AcumuladoCliente, Long> {
    
    @Lock(LockModeType.OPTIMISTIC)
    public AcumuladoCliente findByClienteId(Long id);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    public AcumuladoCliente save(AcumuladoCliente acumulado);
}

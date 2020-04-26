/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.AcumuladoCaja;
import java.io.Serializable;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fafre
 */

public interface AcumuladoCajaRepo extends CrudRepository<AcumuladoCaja, Long> {
        
    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    AcumuladoCaja findTopByMonedaIdAndInstrumentoIdAndCajaId(Long idMoneda,Long idInstrumento, Long idCaja);
    
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    AcumuladoCaja save(AcumuladoCaja caja);
}

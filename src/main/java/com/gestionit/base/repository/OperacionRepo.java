/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;
import com.gestionit.base.domain.Operacion;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fafre
 */
public interface OperacionRepo extends CrudRepository<Operacion,Long> {
    

    Stream<Operacion> findByFechaHoraAfterAndFechaHoraBeforeAndTipoMov(LocalDateTime fechaDesde,LocalDateTime fechaHasta,String tipoMov);
    
    List<Operacion> findByTipoMov(String tipoMov);
    
    List<Operacion> findByTipoMovAndClienteDocumentoStartingWithAndClienteNombreStartingWithAndClienteApellidoStartingWith(String tipoMov,
            String documento, String nombre, String apellido);
    
    List<Operacion> findByCajaId(Long idCaja);

    List<Operacion> findDistinctByCajaIdAndOperacionItemsInstrumentoIdAndOperacionItemsMonedaId(Long idCaja, Long idInstrumento, Long idMoneda);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.Cotizacion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author fafre
 */
public interface CotizacionRepository extends CrudRepository<Cotizacion,Long>{
    
  
    @Query("select c from Cotizacion c where  c.moneda.valor like ?1% "
            + "and c.tipoCambio.valor like ?2% and c.instrumento.valor like ?3% ")
    List<Cotizacion> findByValoresLike(String moneda,
            String tipoCmb,String inst, Sort sort);
    
    @Query("select c from Cotizacion c where  c.moneda.id like ?1% "
            + "and c.tipoCambio.id like ?2% and c.instrumento.id like ?3% ")
    List<Cotizacion> findByIdsLike(Long monedaId,
            Long tipoCmbID,Long instId, Sort sort);
    

    @Query("select  c from Cotizacion c where  c.moneda.valor like ?1% "
            + "and c.tipoCambio.valor like ?2% and c.instrumento.valor like ?3%")            
    Page<Cotizacion> getLastestCotizacionesByMoneda(String moneda,
            String tipoCmb,String inst, Pageable page);
    
    Cotizacion findTopByMonedaValorOrderByFechaDesc(String moneda);
    
    Cotizacion findTopByMonedaValorAndEntidadValorAndTipoCambioValorAndInstrumentoValorOrderByFechaDesc(String moneda,
            String entidad, String tipoCmb,  String instrumento);
    //find from fecha1 to fecha2
    List<Cotizacion> findByFechaAfterAndFechaBefore(LocalDateTime fechaDesde,LocalDateTime fechaHasta);
}

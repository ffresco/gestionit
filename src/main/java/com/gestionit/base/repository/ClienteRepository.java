/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;
import com.gestionit.base.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fafre
 */
public interface ClienteRepository extends CrudRepository<Cliente,Long>{
    
    Cliente findByDocumento(String documento);
    
    @Query("select  c from Cliente c where  c.nombre like ?1% "
            + "and c.apellido like ?2% and c.documento like ?3%")            
    Page<Cliente> getAllLikeNombreApellidoDocumento(String nombre,
            String apellido,String documento, Pageable page);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.FileTextRegistry;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fafre
 */
public interface FileTextRegistryRepo extends CrudRepository<FileTextRegistry, Long>{
    
}

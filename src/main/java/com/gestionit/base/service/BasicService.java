/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;


import java.util.List;

/**
 *
 * @author fafre
 * @param T la entidad que debe manjejar el servicio
 */
public interface BasicService<T> {

    void delete(T entity);

    T getById(Long id);

 
    T saveOrUpdate(T entity);
    
    List<T> findAll();
}

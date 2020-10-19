/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 * @param T1: un searhDTO el objeto que se usa para la busqueda
 * @param T2: un objectDTO que se usa para comunicarse con la vista
 * Example: Envio CotizacionSearchDTO y el Objeto CotizacionDTO
 */
public interface CrudControllerInterface<T1 ,T2> {

    @RequestMapping(value = "/")
    ModelAndView getMainPage(@ModelAttribute T1 searchDTO, BindingResult bindingResult);
  
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ModelAndView search(@ModelAttribute T1 searchDTO, BindingResult bindingResult);

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    ModelAndView getCreatePage(@ModelAttribute T2 objectDTO, BindingResult bindingResult);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    ModelAndView save(@ModelAttribute T2 objectDTO, BindingResult bindingResult);
    
    @RequestMapping(value = "/delete/{id}")
    ModelAndView delete(@PathVariable Long id);

    @RequestMapping(value = "/edit/{id}")
    ModelAndView edit(@PathVariable Long id);




    
}


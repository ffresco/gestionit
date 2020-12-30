/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.domain.dto.RiesgoSearchDTO;
import com.gestionit.base.domain.dto.SearchDTO;

/**
 *
 * @author fafre
 * @param T1: un searhDTO el objeto que se usa para la busqueda
 * @param T2: representa a el modelo
 * Example: Envio CotizacionSearchDTO y el Objeto Cotizacion
 */
public abstract class CrudControllerPaginationInterface<T1 ,T2, T3> {
	
	public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "2";

    @RequestMapping(value = "/")
	abstract String getMainPage(@ModelAttribute T1 searchDTO, BindingResult bindingResult);
    
    abstract ModelAndView getMainPagePaginated( @PathVariable(name = "page") final int pageNumber,
    @PathVariable(name = "page-size") final int pageSize,@ModelAttribute T1 searchDTO, BindingResult bindingResult);
  
    @RequestMapping(value = "/search", method = RequestMethod.POST)
	abstract
    ModelAndView search(@PathVariable(name = "page") final int pageNumber,
            @PathVariable(name = "page-size") final int pageSize,@ModelAttribute (value = "searchDTO") T1 searchDTO, 
            BindingResult bindingResult);

    @RequestMapping(value = "/create", method = RequestMethod.GET)
	abstract
    ModelAndView getCreatePage(@ModelAttribute T2 objectDTO, BindingResult bindingResult);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
	abstract
    ModelAndView save(@ModelAttribute T2 objectDTO, BindingResult bindingResult);
    
    @RequestMapping(value = "/delete/{id}")
	abstract
    String delete(@PathVariable Long id);

    @RequestMapping(value = "/edit/{id}")
	abstract
    ModelAndView edit(@PathVariable Long id);

    @ModelAttribute("pageSize")
	public String getPageSize() {
		return DEFAULT_PAGE_SIZE;
	}
	
    @ModelAttribute("pageNumber")
	public String getPageNumber() {
    	return DEFAULT_PAGE_NUMBER;
    }
    ;
    
    public  SearchDTO<T3> getResponseDto(Page<T3> paginated, int pageNumber, SearchDTO<T3> searchDTO) {
	        final Map<String, Integer> page = searchDTO.getPage();
            page.put("currentPage", pageNumber);
            page.put("totalPages", paginated.getTotalPages());
            page.put("totalElements", (int) paginated.getTotalElements());
            searchDTO.setContents(paginated.getContent());
            return searchDTO;
	}

	@ModelAttribute("contextPath")
	public String getContextPaht() {
		
		return getClass().getAnnotation(RequestMapping.class).value()[0];
	}
    
}


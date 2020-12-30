/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author cbova
 */
public class SearchDTO<T>{
    

	private Map<String, Integer> page = new HashMap<>();

	private List<T> contents;


	public SearchDTO() {
    }
   

	public Map<String, Integer> getPage() {
		return page;
	}

	public void setPage(Map<String, Integer> page) {
		this.page = page;
	}


	public void setContents(List<T> list) {
		this.contents = list;
		
	}


	public List<T> getContents() {
		return contents;
	}
	

    
    
}

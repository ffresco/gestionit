/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Cbova
 */

@Entity
@Table(name = "origen_amenaza")
public class OrigenAmenaza implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3043062943405229790L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
      
    protected String origen;
    
    


 	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrigen() {
		return origen;
	}


	public void setOrigen(String origen) {
		this.origen = origen;
	}


	public OrigenAmenaza() {
     }
     

     public OrigenAmenaza(String origen) {
 		super();
 		this.origen = origen;
 	}

	@Override
    public String toString() {
        return "Origen Amenaza{" + "id=" + id +  ", Origen=" + Optional.ofNullable(origen) + 
                +'}';
    }
    
    
    
    
}

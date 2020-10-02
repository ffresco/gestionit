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


/**
 *
 * @author Cbova
 */

@Entity
public class Amenaza implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3043062943405229790L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
 
    protected String codigo;
      
    protected String origen;
    
    protected String tipo;
    


 	public Amenaza() {
     }
     

     public Amenaza(String codigo, String origen, String tipo) {
 		super();
 		this.codigo = codigo;
 		this.origen = origen;
 		this.tipo = tipo;
 	}

	@Override
    public String toString() {
        return "Amenaza{" + "id=" + id + ", Codigo=" + codigo + ", Origen=" + Optional.ofNullable(origen) + 
                ", Tipo=" + Optional.ofNullable(tipo) +'}';
    }
    
    
    
    
}

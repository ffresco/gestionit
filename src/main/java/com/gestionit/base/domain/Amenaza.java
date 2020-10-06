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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


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
      
 
    @OneToOne
    @JoinColumn(name="fk_origen_amenaza")
    protected OrigenAmenaza origen;
    
    protected String tipo;
    


 	public Amenaza() {
     }
     

     public Amenaza(String codigo, OrigenAmenaza origen, String tipo) {
 		super();
 		this.codigo = codigo;
 		this.origen = origen;
 		this.tipo = tipo;
 	}
     


 	public Long getId() {
 		return id;
 	}


 	public void setId(Long id) {
 		this.id = id;
 	}
     
     
     public String getCodigo() {
 		return codigo;
 	}


 	public void setCodigo(String codigo) {
 		this.codigo = codigo;
 	}


 	public OrigenAmenaza getOrigen() {
 		return origen;
 	}


 	public void setOrigen(OrigenAmenaza origen) {
 		this.origen = origen;
 	}


 	public String getTipo() {
 		return tipo;
 	}


 	public void setTipo(String tipo) {
 		this.tipo = tipo;
 	}


	@Override
    public String toString() {
        return "Amenaza{" + "id=" + id + ", Codigo=" + codigo + ", Origen=" + Optional.ofNullable(origen) + 
                ", Tipo=" + Optional.ofNullable(tipo) +'}';
    }

    
    
    
}

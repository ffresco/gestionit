/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**
 *
 * @author Cbova
 */

@MappedSuperclass
public class ItemRiesgo implements Serializable{
  

	/**
	 * 
	 */
	private static final long serialVersionUID = -3043062943405229790L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
 
    protected Integer valor;
      
    protected String calificacion;
    
   
    public ItemRiesgo() {
    }
    
    

    public ItemRiesgo(Integer valor, String calificacion) {
		super();
		this.valor = valor;
		this.calificacion = calificacion;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

	public Integer getValor() {
		return valor;
	}



	public void setValor(Integer valor) {
		this.valor = valor;
	}



	public String getCalificacion() {
		return calificacion;
	}



	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}



	@Override
    public String toString() {
        return this.getClass()+" {" + "id=" + id + ", Valor=" + valor + ", Calificacion=" + calificacion +
                " }";
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

/**
 *
 * @author fafre
 */
public class ClienteSearchDTO {
    
    private String documento;
    private String nombre;
    private String apellido;

    public ClienteSearchDTO() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "ClienteSearchDTO{" + "documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido + '}';
    }
    
    
    
}

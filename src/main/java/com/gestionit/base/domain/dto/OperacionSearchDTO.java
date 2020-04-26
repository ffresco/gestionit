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
public class OperacionSearchDTO {
    
    private Long operacion;
    private String documento;
    private String nombre;
    private String apellido;
    private String fechaDesde;
    private String fechaHasta;

    public OperacionSearchDTO() {
    }

    public Long getOperacion() {
        return operacion;
    }

    public void setOperacion(Long operacion) {
        this.operacion = operacion;
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

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public String toString() {
        return "OperacionSearchDTO{" + "operacion=" + operacion + ", documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + '}';
    }
    

    
}

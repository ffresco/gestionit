/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author fafre
 */
@Entity
@Table(name = "archivos_generados")
public class FileTextRegistry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "semana")
    private LocalDate semana;
    
    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "observaciones")
    private String observaciones;

    public FileTextRegistry() {
    }

  
    public FileTextRegistry(LocalDateTime fechaCreacion, LocalDate semana, LocalDate fechaDesde, LocalDate fechaHasta, String fileName, String estado, String observaciones) {
        this.fechaCreacion = fechaCreacion;
        this.semana = semana;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fileName = fileName;
        this.estado = estado;
        this.observaciones = observaciones;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getSemana() {
        return semana;
    }

    public void setSemana(LocalDate semana) {
        this.semana = semana;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
    

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "FileTextRegistry{" + "id=" + id + ", fechaCreacion=" + fechaCreacion + ", semana=" + semana + ", fileName=" + fileName + ", estado=" + estado + ", observaciones=" + observaciones + '}';
    }
    
    
    
   
}

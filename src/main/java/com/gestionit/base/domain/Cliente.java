/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Representa un cliente Fisico
 * @author fafre
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "fk_param_tipodoc")
    private Parametro tipoDocumento;
 
    @Column(name = "documento",unique = true)
    private String documento;
    
    @Column(name = "venc_doc")
    private LocalDate vencimientoDoc;
    
    @Column(name = "fecha_nac")
    private LocalDate fechaNacimiento;
        
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido")
    private String apellido;
    
    @OneToOne
    @JoinColumn(name = "param_estado_civ")
    private Parametro estadoCivil;
    
    @OneToOne
    @JoinColumn(name = "param_pais_origen")
    private Parametro paisOrigen;
    
    @OneToOne
    @JoinColumn(name = "param_pais_res")
    private Parametro paisResidencia;
    
    //domicilio--
    @Column(name="direccion")
    private String direccion;
    
    @Column(name="localidad")
    private String Localidad;          
    
    @OneToOne
    @JoinColumn(name="fk_param_provincia")
    private Parametro provincia;
    
    @Column(name="codigo_postal")
    private String codigoPostal;
    //--fin domicilio
    
    @Column(name = "telefono")
    private String telefono;
        
    @Column(name = "email")
    private String email;    
    
    @Column(name = "pep")
    private boolean pep;
    
    @Column(name ="firma_pep")
    private LocalDate fechaFirmaPep;
    
    @Column(name = "observaciones")
    private String observaciones;
   
    
    @OneToOne(mappedBy = "cliente",cascade = CascadeType.PERSIST,optional = false,fetch = FetchType.EAGER,orphanRemoval = true)
    private AcumuladoCliente acumulado;

    public Cliente() {
    }

    public Cliente(Parametro tipoDocumento, String documento, LocalDate vencimientoDoc, 
            LocalDate fechaNacimiento, String nombre, String apellido, Parametro estadoCivil, 
            Parametro paisOrigen, Parametro paisResidencia, String telefono, String email, 
            AcumuladoCliente acumulado) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.vencimientoDoc = vencimientoDoc;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estadoCivil = estadoCivil;
        this.paisOrigen = paisOrigen;
        this.paisResidencia = paisResidencia;
        this.telefono = telefono;
        this.email = email;
        this.acumulado = acumulado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parametro getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Parametro tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getVencimientoDoc() {
        return vencimientoDoc;
    }

    public void setVencimientoDoc(LocalDate vencimientoDoc) {
        this.vencimientoDoc = vencimientoDoc;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public Parametro getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Parametro estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Parametro getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(Parametro paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public Parametro getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(Parametro paisResidencia) {
        this.paisResidencia = paisResidencia;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AcumuladoCliente getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(AcumuladoCliente acumulado) {
        this.acumulado = acumulado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }

    public Parametro getProvincia() {
        return provincia;
    }

    public void setProvincia(Parametro provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public boolean isPep() {
        return pep;
    }

    public void setPep(boolean pep) {
        this.pep = pep;
    }

    public LocalDate getFechaFirmaPep() {
        return fechaFirmaPep;
    }

    public void setFechaFirmaPep(LocalDate fechaFirmaPep) {
        this.fechaFirmaPep = fechaFirmaPep;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", tipoDocumento=" + tipoDocumento + ", documento=" + documento + ", vencimientoDoc=" + vencimientoDoc + ", fechaNacimiento=" + fechaNacimiento + ", nombre=" + nombre + ", apellido=" + apellido + ", estadoCivil=" + estadoCivil + ", paisOrigen=" + paisOrigen + ", paisResidencia=" + paisResidencia + ", direccion=" + direccion + ", Localidad=" + Localidad + ", provincia=" + provincia + ", codigoPostal=" + codigoPostal + ", telefono=" + telefono + ", email=" + email + ", pep=" + pep + ", fechaFirmaPep=" + fechaFirmaPep + ", observaciones=" + observaciones + ", acumulado=" + Optional.ofNullable(acumulado) + '}';
    }
    
    
    

    
  
    

    
    
    
}

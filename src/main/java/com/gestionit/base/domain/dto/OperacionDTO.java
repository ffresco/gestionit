/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.TopeCompra;
import java.util.Optional;

/**
 *
 * @author fafre
 */
public class OperacionDTO {

    private Operacion operacion;
    private Cliente cliente;
    private OperacionItem opO;
    private OperacionItem opD;
    private TopeCompra tope;
    
    //para pantalla de cajas
    private AcumuladoCaja acumCajaO;
    private AcumuladoCaja acumCajaD;

    private boolean modificable;
    private boolean procesado;
    private boolean readOnly;

    private boolean compra;
    private boolean venta;
    private boolean canje;
    private boolean arbitraje;

    public OperacionDTO(Operacion operacion, Cliente cliente, OperacionItem opO, OperacionItem opD) {
        this.operacion = operacion;
        this.cliente = cliente;
        this.opO = opO;
        this.opD = opD;
    }

    public OperacionDTO() {
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }

    public OperacionItem getOpO() {
        return opO;
    }

    public void setOpO(OperacionItem opO) {
        this.opO = opO;
    }

    public OperacionItem getOpD() {
        return opD;
    }

    public void setOpD(OperacionItem opD) {
        this.opD = opD;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public TopeCompra getTope() {
        return tope;
    }

    public void setTope(TopeCompra tope) {
        this.tope = tope;
    }

    public AcumuladoCaja getAcumCajaO() {
        return acumCajaO;
    }

    public void setAcumCajaO(AcumuladoCaja acumCajaO) {
        this.acumCajaO = acumCajaO;
    }

    public AcumuladoCaja getAcumCajaD() {
        return acumCajaD;
    }

    public void setAcumCajaD(AcumuladoCaja acumCajaD) {
        this.acumCajaD = acumCajaD;
    }
    

    public boolean isCompra() {
        return compra;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isCanje() {
        return canje;
    }

    public void setCanje(boolean canjeFlag) {
        this.canje = canjeFlag;
    }

    public boolean isArbitraje() {
        return arbitraje;
    }

    public void setArbitraje(boolean arbitraje) {
        this.arbitraje = arbitraje;
    }

    private void resetFlags() {
        compra = false;
        venta = false;
        canje = false;
        arbitraje = false;

    }

   public void setFlagsTipoOperacion() {
        String tipoOp = this.getOperacion().getTipoOp().getValor();
        this.resetFlags();
        if (tipoOp!=null) {
            this.setCompra(tipoOp.equals("CMP"));
            this.setVenta(tipoOp.equals("VTA"));
            this.setCanje(tipoOp.equals("CANJE"));
            this.setArbitraje(tipoOp.equals("ARBITRAJE"));
        }
    }    

    /*
    * Se spuede modificar, no se puede guardar y no esta procesado
    */
    public void configAltaScreen(OperacionDTO opDTO) {
        opDTO.setModificable(true);
        opDTO.setProcesado(false);
        opDTO.setReadOnly(false);

    }

    public void configProcessScreen(OperacionDTO opDTO) {
        opDTO.setModificable(false);
        opDTO.setProcesado(true);
        opDTO.setReadOnly(false);

    }

    public void configReadOnlyScreen(OperacionDTO opDTO) {
        opDTO.setModificable(false);
        opDTO.setProcesado(true);
        opDTO.setReadOnly(true);
    }
   
    @Override
    public String toString() {
        return "OperacionDTO{" + "operacion=" + Optional.ofNullable(operacion) + ", cliente=" + cliente + ", opO=" + Optional.ofNullable(opO) + ", opD=" + Optional.ofNullable(opD) + ", modificable=" + modificable + ", procesado=" + procesado + ", readOnly=" + readOnly + '}';
    }


    
}

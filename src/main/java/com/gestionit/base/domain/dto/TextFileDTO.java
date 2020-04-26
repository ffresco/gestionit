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
public class TextFileDTO {

    private String nombre;
    private String documento;
    private String dni;
    private float monto;

    //campos en orden para el archivo
    private String codDiseno_1;
    private String codEntidad_2;
    private String fechaOp_3;
    private String codJurisdiccion_4;
    private String tipoOp_5;
    private String numRegistro_6;
    private String numEntidad_7;
    private String tipoEntidad_8;
    private String numIdentificador_9;
    private String denominacionCli_10;
    private String residenciaCli_11;
    private String condicionCli_12;
    private String codigoCorresponsal_13;
    private String codInstrumentoVtaCmp_14;
    private String codInstrumentoRecEnt_15;
    private String codPaisBeneficiario_16;
    private String denominacionBeneficiario_17;
    private String paisOrigenCli_18;
    private String codConcepto_19;
    private String fechaOpOriginal_20;
    private String codMoneda_21;
    private String importeMndRecibida_22;
    private String ImporteMndBase_23;
    private String numOficializacion_24;
    private String sinUso_25;
    private String rectificativa_26;

    public TextFileDTO(String nombre, String documento, String dni, float monto) {
        this.nombre = nombre;
        this.documento = documento;
        this.dni = dni;
        this.monto = monto;
    }

    public TextFileDTO() {
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
    
    //Nuevos metodos

    public String getCodDiseno_1() {
        return codDiseno_1;
    }

    public void setCodDiseno_1(String codDiseno_1) {
        this.codDiseno_1 = codDiseno_1;
    }

    public String getCodEntidad_2() {
        return codEntidad_2;
    }

    public void setCodEntidad_2(String codEntidad_2) {
        this.codEntidad_2 = codEntidad_2;
    }

    

    public String getFechaOp_3() {
        return fechaOp_3;
    }

    public void setFechaOp_3(String fechaOp_3) {
        this.fechaOp_3 = fechaOp_3;
    }

    public String getCodJurisdiccion_4() {
        return codJurisdiccion_4;
    }

    public void setCodJurisdiccion_4(String codJurisdiccion_4) {
        this.codJurisdiccion_4 = codJurisdiccion_4;
    }

    public String getTipoOp_5() {
        return tipoOp_5;
    }

    public void setTipoOp_5(String tipoOp_5) {
        this.tipoOp_5 = tipoOp_5;
    }

    public String getNumRegistro_6() {
        return numRegistro_6;
    }

    public void setNumRegistro_6(String numRegistro_6) {
        this.numRegistro_6 = numRegistro_6;
    }

    public String getNumEntidad_7() {
        return numEntidad_7;
    }

    public void setNumEntidad_7(String numEntidad_7) {
        this.numEntidad_7 = numEntidad_7;
    }

    public String getTipoEntidad_8() {
        return tipoEntidad_8;
    }

    public void setTipoEntidad_8(String tipoEntidad_8) {
        this.tipoEntidad_8 = tipoEntidad_8;
    }

 
    public String getNumIdentificador_9() {
        return numIdentificador_9;
    }

    public void setNumIdentificador_9(String numIdentificador_9) {
        this.numIdentificador_9 = numIdentificador_9;
    }

    public String getDenominacionCli_10() {
        return denominacionCli_10;
    }

    public void setDenominacionCli_10(String denominacionCli_10) {
        this.denominacionCli_10 = denominacionCli_10;
    }

    public String getResidenciaCli_11() {
        return residenciaCli_11;
    }

    public void setResidenciaCli_11(String residenciaCli_11) {
        this.residenciaCli_11 = residenciaCli_11;
    }

    public String getCondicionCli_12() {
        return condicionCli_12;
    }

    public void setCondicionCli_12(String condicionCli_12) {
        this.condicionCli_12 = condicionCli_12;
    }

 
    public String getCodigoCorresponsal_13() {
        return codigoCorresponsal_13;
    }

    public void setCodigoCorresponsal_13(String codigoCorresponsal_13) {
        this.codigoCorresponsal_13 = codigoCorresponsal_13;
    }

    public String getCodInstrumentoVtaCmp_14() {
        return codInstrumentoVtaCmp_14;
    }

    public void setCodInstrumentoVtaCmp_14(String codInstrumentoVtaCmp_14) {
        this.codInstrumentoVtaCmp_14 = codInstrumentoVtaCmp_14;
    }

    public String getCodInstrumentoRecEnt_15() {
        return codInstrumentoRecEnt_15;
    }

    public void setCodInstrumentoRecEnt_15(String codInstrumentoRecEnt_15) {
        this.codInstrumentoRecEnt_15 = codInstrumentoRecEnt_15;
    }

  
    public String getCodPaisBeneficiario_16() {
        return codPaisBeneficiario_16;
    }

    public void setCodPaisBeneficiario_16(String codPaisBeneficiario_16) {
        this.codPaisBeneficiario_16 = codPaisBeneficiario_16;
    }

    public String getDenominacionBeneficiario_17() {
        return denominacionBeneficiario_17;
    }

    public void setDenominacionBeneficiario_17(String denominacionBeneficiario_17) {
        this.denominacionBeneficiario_17 = denominacionBeneficiario_17;
    }

    public String getPaisOrigenCli_18() {
        return paisOrigenCli_18;
    }

    public void setPaisOrigenCli_18(String paisOrigenCli_18) {
        this.paisOrigenCli_18 = paisOrigenCli_18;
    }

    public String getCodConcepto_19() {
        return codConcepto_19;
    }

    public void setCodConcepto_19(String codConcepto_19) {
        this.codConcepto_19 = codConcepto_19;
    }

    public String getFechaOpOriginal_20() {
        return fechaOpOriginal_20;
    }

    public void setFechaOpOriginal_20(String fechaOpOriginal_20) {
        this.fechaOpOriginal_20 = fechaOpOriginal_20;
    }

    public String getCodMoneda_21() {
        return codMoneda_21;
    }

    public void setCodMoneda_21(String codMoneda_21) {
        this.codMoneda_21 = codMoneda_21;
    }

    public String getImporteMndRecibida_22() {
        return importeMndRecibida_22;
    }

    public void setImporteMndRecibida_22(String importeMndRecibida_22) {
        this.importeMndRecibida_22 = importeMndRecibida_22;
    }

    public String getImporteMndBase_23() {
        return ImporteMndBase_23;
    }

    public void setImporteMndBase_23(String ImporteMndBase_23) {
        this.ImporteMndBase_23 = ImporteMndBase_23;
    }

 
    public String getNumOficializacion_24() {
        return numOficializacion_24;
    }

    public void setNumOficializacion_24(String numOficializacion_24) {
        this.numOficializacion_24 = numOficializacion_24;
    }

    public String getSinUso_25() {
        return sinUso_25;
    }

    public void setSinUso_25(String sinUso_25) {
        this.sinUso_25 = sinUso_25;
    }

    public String getRectificativa_26() {
        return rectificativa_26;
    }

    public void setRectificativa_26(String rectificativa_26) {
        this.rectificativa_26 = rectificativa_26;
    }
    
    

}

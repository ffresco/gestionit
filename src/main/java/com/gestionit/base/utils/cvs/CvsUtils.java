/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.utils.cvs;

import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.dto.TextFileDTO;
import com.gestionit.base.service.OperacionService;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author fafre
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CvsUtils {

    //The one asked by BCRA
    private static final CsvPreference OPCAM_DELIMITED = new CsvPreference.Builder('"', ';', "\r\n").build();

    /**
     * Sets up the processors used for the examples. There are 10 CSV columns,
     * so 10 processors are defined. All values are converted to Strings before
     * writing (there's no need to convert them), and null values will be
     * written as empty columnns (no need to convert them to "").
     *
     * @return the cell processors
     */
    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors;
        processors = new CellProcessor[]{
            new NotNull(), // customerNo (must be unique)
            new NotNull(), // firstName
            new NotNull(), // lastName
            new NotNull(), // mailingAddress
        };

        return processors;
    }

    /**
     * retorna true si lo pudo generar, false si sale por excepcion
     */
    @Transactional(readOnly = true)
    public boolean writeWithCsvBeanWriter(OperacionService service, FileTextRegistry fileReg) {
        boolean generado = false;

        //Implemento un try catch con recursos para no tener que cerrar los archivos
        try (ICsvBeanWriter beanWriter = new CsvBeanWriter(new FileWriter("txt/" + fileReg.getFileName()),
                OPCAM_DELIMITED);
                Stream<Operacion> todoStream = service.getStreamOpOpCam(fileReg);) {

            //Creo la cabecera del archivo
            String[] header = getOpCamHeader();
            //creo el procesador de las filas del cuerco
            final CellProcessor[] processors = getProcessors();

            //-->write the header lo saco porque por ahora el opcam va sin cabecera
            //beanWriter.writeHeader(header);
            //--Aca hago la lectura y escritura con el Stream abierto en el try
            todoStream.forEach(todo -> {
                TextFileDTO rowFile = convertOpToFileTextDTO(todo);
                try {
                    beanWriter.write(rowFile, header, processors);
                } catch (IOException ex) {
                    Logger.getLogger(CvsUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            //--Fin de la letura       

            generado = true;

        } catch (IOException ex) {
            generado = false;
            fileReg.setObservaciones(ex.getMessage());
            System.out.println("Se cancelo la generacion del archivo " + ex);
        }
        return generado;
    }

    private String[] getOpCamHeader() {
        // the header elements are used to map the bean values to each column (names must match) so name are equals to the properties names
        final String[] header = new String[]{"nombre", "documento", "dni", "monto"};
        return header;
    }

    private static TextFileDTO convertOpToFileTextDTO(Operacion op) {
        TextFileDTO fileDTO = new TextFileDTO(op.getCliente().getNombre(), op.getCliente().getDocumento()+"", "dni", 0);

        /*
        TextFileDTO fileDTO = new TextFileDTO();
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyyMMdd");

        //construir el archivo
        fileDTO.setCodDiseno_1("2713");
        fileDTO.setCodEntidad_2("LODEBE"); 
        fileDTO.setFechaOp_3(op.getFechaHora().format(formatter3));
        fileDTO.setCodJurisdiccion_4("01");
        fileDTO.setTipoOp_5(op.getTipoOp().getCodigo());
        fileDTO.setNumRegistro_6(op.getId().toString());
        fileDTO.setNumEntidad_7();
        fileDTO.setTipoEntidad_8();
        fileDTO.setNumIdentificador_9();
        fileDTO.setDenominacionCli_10();
        fileDTO.setResidenciaCli_11();
        fileDTO.setCondicionCli_12();
        fileDTO.setCodigoCorresponsal_13();
        fileDTO.setCodInstrumentoVtaCmp_14();
        fileDTO.setCodInstrumentoRecEnt_15();
        fileDTO.setCodPaisBeneficiario_16();
        fileDTO.setDenominacionBeneficiario_17();
        fileDTO.setPaisOrigenCli_18();
        fileDTO.setCodConcepto_19();
        fileDTO.setFechaOpOriginal_20();
        fileDTO.setCodMoneda_21();
        fileDTO.setImporteMndRecibida_22();
        fileDTO.setImporteMndBase_23();
        fileDTO.setNumOficializacion_24();
        fileDTO.setSinUso_25();
        fileDTO.setRectificativa_26();
        */

        return fileDTO;
    }
}

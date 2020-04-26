/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.dto.AuditoriaDTO;
import com.gestionit.base.domain.dto.OperacionReportDTO;
import com.gestionit.base.domain.dto.TextFileDTO;
import com.gestionit.base.repository.FileTextRegistryRepo;
import com.gestionit.base.service.OperacionService;
import com.gestionit.base.utils.cvs.CvsProducerAdministrator;
import com.gestionit.base.utils.cvs.CvsUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleCsvReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleReportExportConfiguration;
import net.sf.jasperreports.export.SimpleTextExporterConfiguration;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.WriterExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @rodrti hjhh
 */
@Controller
@RequestMapping(value="/auditoria")
public class AuditoriaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditoriaController.class);
    private OperacionService operacionService;
    private FileTextRegistryRepo fileTextRegistryRepo;
    private CvsProducerAdministrator cvsProducerAdministrator;

    @Autowired
    public AuditoriaController(OperacionService operacionService, FileTextRegistryRepo fileTextRegistryRepo,
            CvsProducerAdministrator cvsProducerAdministrator) {
        this.operacionService = operacionService;
        this.fileTextRegistryRepo = fileTextRegistryRepo;
        this.cvsProducerAdministrator = cvsProducerAdministrator;
    }

    @RequestMapping("/")
    public ModelAndView getMainPage() {
        List<FileTextRegistry> archivosCreados = (List<FileTextRegistry>) fileTextRegistryRepo.findAll();
        ModelAndView mav = new ModelAndView("auditoria", "archivos", archivosCreados);
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView getCreatePage(@ModelAttribute AuditoriaDTO dto, BindingResult result) {
        dto.setPorSemana(true);
        ModelAndView mav = new ModelAndView("auditoria_create", "dto", dto);
        return mav;
    }

    @RequestMapping(value = "/getfile/{id}")
    void getFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        FileTextRegistry fileReg = fileTextRegistryRepo.findOne(id);
        String path = "txt/" + fileReg.getFileName();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        response.setContentType("application/txt");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileReg.getFileName() + "\"");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }

    @RequestMapping(path = "/produce", method = RequestMethod.POST)
    public ModelAndView generateReport(@ModelAttribute(value = "dto") AuditoriaDTO dto,
            BindingResult bindingResult) {
        LOGGER.info("--Le pegue al print --Generando TXT-");
        LOGGER.info("Archivo pedido sobre esta semana " + dto.getSemana());
        
        //1-Genero el pedido----
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        LocalDate fechaDesde,fechaHasta;
        if (dto.isPorSemana()) {
            //Valido la semana no sea null
            if (dto.getSemana().isEmpty()) {
                bindingResult.reject("3", "la valor seleccionado es invalido, seleccione un dia valido");
                return getCreatePage(dto, bindingResult);
            }
            //Busco el lunes de esa semana           
            LocalDate formatDateTime = LocalDate.parse(dto.getSemana(), formatter);
            fechaDesde = formatDateTime.with(DayOfWeek.MONDAY);
            fechaHasta = fechaDesde.plusDays(6);

        } else {
            if (dto.getFechaDesde().isEmpty() || dto.getFechaHasta().isEmpty()) {
                bindingResult.reject("3", "la valor seleccionado es invalido, seleccione un dia valido");
                return getCreatePage(dto, bindingResult);
            }
            fechaDesde = LocalDate.parse(dto.getFechaDesde(), formatter);
            fechaHasta = LocalDate.parse(dto.getFechaHasta(), formatter);

        }
        
        String fileName = fechaDesde.toString() + "_" + fechaHasta.toString() + "_OPCAM.txt";
        FileTextRegistry pedido = new FileTextRegistry(LocalDateTime.now(), fechaDesde, fechaDesde, fechaHasta, fileName, "", "");
        System.out.println("Pedido resultante " + pedido);
        //1-Fin de generacion del pedido
        
        //2-Mando a resolver el pedido
        boolean aceptado = cvsProducerAdministrator.encargarCVS(pedido, operacionService, fileTextRegistryRepo);
        String mensaje;
        if (!aceptado) {
            System.out.println("TRABAJO RECHAZADO ");
            bindingResult.reject("1", "el sitema se encuentra generando un archivo y no puede tomar este pedido");
        } else {
            bindingResult.reject("2", "su arhivo esta siendo generado");
            System.out.println("El trabajo FUE ACEPTADO");
        }
        return getCreatePage(dto, bindingResult);
    }

}

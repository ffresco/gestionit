/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.TopeCompra;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.domain.dto.OperacionReportDTO;
import com.gestionit.base.domain.dto.OperacionSearchDTO;
import com.gestionit.base.domain.dto.builder.OpComercialDTOBuilder;
import com.gestionit.base.domain.dto.builder.OpDTOBuilder;
import com.gestionit.base.domain.enums.Estado;
import com.gestionit.base.domain.validator.OperacionProcessValidator;
import com.gestionit.base.service.ClienteService;
import com.gestionit.base.service.CotizacionService;
import com.gestionit.base.service.OperacionService;
import com.gestionit.base.service.UserService;
import com.gestionit.base.service.strategy.operacion.OperacionHandler;
import com.gestionit.base.service.strategy.operacion.OperacionHandlerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
@RequestMapping(value = "/operaciones")
public class OperacionesController implements CrudControllerInterface<OperacionSearchDTO, OperacionDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperacionesController.class);
    private ClienteService clienteService;
    private OperacionService operacionService;
    private UserService userService;
    private DataMaster dataMaster;
    private CotizacionService cotizacionService;
    private OperacionProcessValidator opProcValidator;
    private OperacionHandlerFactory operacionHandlerFactory;
    private OpDTOBuilder dTOBuilder;

    @Autowired
    public OperacionesController(ClienteService clienteService, OperacionService operacionService,
            UserService userService, CotizacionService cotizacionService, DataMaster dataMaster,
            OperacionProcessValidator opv, OperacionHandlerFactory ohf, OpComercialDTOBuilder dtoB) {
        this.clienteService = clienteService;
        this.operacionService = operacionService;
        this.userService = userService;
        this.dataMaster = dataMaster;
        this.cotizacionService = cotizacionService;
        this.opProcValidator = opv;
        this.operacionHandlerFactory = ohf;
        this.dTOBuilder = dtoB;

    }

    @Override
    public ModelAndView getMainPage(OperacionSearchDTO searchDTO, BindingResult bindingResult) {
        List<Operacion> operaciones = operacionService.findAllByTipoMovimiento(OpDTOBuilder.OPERACION_COMERCIAL);
        ModelAndView mav = new ModelAndView("operaciones");
        mav.addObject("operaciones", operaciones);
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

    @Override
    public ModelAndView search(@ModelAttribute(value = "searchDTO") OperacionSearchDTO searchDTO,
            BindingResult bindingResult) {
        System.out.println("DTO search " + searchDTO);
        List<Operacion> operaciones = operacionService.findAllContaining(searchDTO,OpDTOBuilder.OPERACION_COMERCIAL);
        ModelAndView mav = new ModelAndView("operaciones");
        mav.addObject("operaciones", operaciones);
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

    @RequestMapping(value = "/operar/{id}")
    public ModelAndView operar(@PathVariable Long id) {
        LOGGER.debug("----Preparando un cliente para operar -------");
        Cliente clienteAEditar = clienteService.getById(id);
        OperacionDTO opDTO = new OperacionDTO();
        opDTO.setCliente(clienteAEditar);
        DataBinder dataBinder = new DataBinder(opDTO);
        return getCreatePage(opDTO, dataBinder.getBindingResult());
    }

    @RequestMapping(value = "/operardni")
    public ModelAndView operarDni(@RequestParam Map<String, String> parametros) {
        LOGGER.debug("----Preparando un cliente para operar -------");
        System.out.println(parametros.get("dni"));
        String dni = parametros.get("dni");
        Cliente clienteAEditar = clienteService.findByDocumento(dni);
        System.out.println("cliente a editar " + clienteAEditar);
        if (clienteAEditar == null) {
            return getMainPage(null, null);
        }
        OperacionDTO opDTO = new OperacionDTO();
        opDTO.setCliente(clienteAEditar);
        DataBinder dataBinder = new DataBinder(opDTO);
        return getCreatePage(opDTO, dataBinder.getBindingResult());
    }

    @Override
    public ModelAndView getCreatePage(OperacionDTO opDTO, BindingResult bindingResult) {
        LOGGER.debug("-------Le peuge a main page------------");

        //recupero el cliente
        //Cliente cliente = clienteService.findByDocumento("27444999");
        Cliente cliente = opDTO.getCliente();
        LOGGER.info("El cliente recuperado para la op : " + cliente.toString());

        //Genero el DTO -- Esto era cuando no estaba enganchado
        //OperacionDTO opDTO = new OperacionDTO();
        //opDTO.setCliente(cliente);
        dTOBuilder.buildHeader(opDTO, bindingResult);
        dTOBuilder.buildBody(opDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return getMainPage(null, bindingResult);
        }

        LOGGER.info("Cree el siguiente dto para operar : " + opDTO);

        //seto los flags de configuracion
        opDTO.configAltaScreen(opDTO);

        //Preparo el moddel and view
        ModelAndView mav = new ModelAndView("operaciones_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;

    }

    @RequestMapping(value = "/save", params = {"procesar"})
    public ModelAndView process(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.debug("--------------------Entre al procesar------------------");
        ModelAndView mav = new ModelAndView("operaciones_create");
        mav.addObject("operacionDTO", opDTO);

        //recupero la operacion
        Operacion op = opDTO.getOperacion();
        LOGGER.info("Esta es la op procesar : " + op);

        //implemantar un factory que me de un manejador
        OperacionHandler opHandler = operacionHandlerFactory.getHandler(op.getTipoOp().getValor());

        //no encontro manejador
        if (opHandler == null) {
            opDTO.configAltaScreen(opDTO);
            result.reject("operacion.not.implemented", "la operacion: " + op.getTipoOp().getValor() + "no"
                    + " ha sido implementada o el sistema, verifique la carga de parametros, si la operacion"
                    + " corresponde a un nuevo tipo solicite la implementacion de la funcionalidad ");
            return mav;
        }

        //valido la seleccion de combos (por ahora la validacion es general para todas las op)
        opProcValidator.validate(opDTO, result);
        if (result.hasErrors()) {
            //seto los flags de configuracion de pantalla
            opDTO.configAltaScreen(opDTO);
            return mav;
        }

        //dependiendo que selecciono activa un tipo deoperacion
        opDTO.setFlagsTipoOperacion();
        opHandler.generarOperacion(opDTO, result);
        //si el handler tuvo algun error
        if (result.hasErrors()) {
            opDTO.configAltaScreen(opDTO);
            return mav;
        }
        //pongo un flag para invalidar el resto de los campos
        opDTO.configProcessScreen(opDTO);

        //Cambio el estado de la Cotizacion a procesado
        op.setEstado(Estado.PROCESADO.getEstado());

        //guado todo en el mav
        mav.addObject("operacionDTO", opDTO);
        return mav;
    }

    @RequestMapping(value = "/save", params = {"modificar"})
    public ModelAndView modif(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.debug("----------Le pegue al modificar---------");
        //Seteo nuevamente los flags de modificacion
        opDTO.configAltaScreen(opDTO);

        //Cambio el estado a nuevo
        opDTO.getOperacion().setEstado(Estado.NUEVO.getEstado());

        //guado todo en el mav
        ModelAndView mav = new ModelAndView("operaciones_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;

    }

    @Override
    @RequestMapping(value = "/save", params = {"guardar"})
    public ModelAndView save(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.info("-----Entre al save de operaciones------");
        LOGGER.info("OperacionDTO recuperada" + opDTO.getOperacion());

        //Le cambio el estado al OK
        opDTO.getOperacion().setEstado(Estado.OK.getEstado());

        dTOBuilder.setTipoMov(opDTO);

        //construyo  el objeto a grabar
        //Operacion opAGravar = operacionService.buildOperacionFromDTO(opDTO);
        Operacion opAGravar = dTOBuilder.buildOpToSaveFromDTO(opDTO);
        LOGGER.info("Esta es la operacion a grabar " + opAGravar);

        //Si el estado es observado lo guardo con comentarios
        //Guardo la operacion
        Operacion op = operacionService.saveComercial(opDTO.getOperacion());
        //op.setUser(userService.getCurrentUser());
        opDTO.setOperacion(op);
        LOGGER.debug("operacion gravada " + op);
        opDTO.configReadOnlyScreen(opDTO);
        ModelAndView mav = new ModelAndView("operaciones_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;

    }

    @Override
    public ModelAndView delete(@PathVariable Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelAndView edit(@PathVariable Long id) {
        LOGGER.debug("-----Entre al edi de operaciones------");
        LOGGER.debug("Id a editar " + id);
        Operacion op = operacionService.findOne(id);
        LOGGER.debug("Operacion recuperada " + op);
        OperacionDTO opDTO = new OperacionDTO(op, op.getCliente(), op.getOpItemO(), op.getOpItemD());
        opDTO.configReadOnlyScreen(opDTO);
        ModelAndView mav = new ModelAndView("operaciones_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;
    }

    @RequestMapping(value = "/testpdf")
    void getFile(HttpServletResponse response) throws IOException {

        System.out.println("Headr " + response);
        System.out.println("Print header " + response.getHeader("X-Frame-Options"));
        System.out.println(response.getHeaderNames());
        String fileName = "report.pdf";
        String path = "pdf/" + fileName;

        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);

        response.setContentType("application/pdf");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }

    @RequestMapping(path = "/pdf/{id}", method = RequestMethod.GET)
    public void report(HttpServletResponse response, @PathVariable Long id) {
        LOGGER.info("--Le pegue al print ---Generando PDF de Impresion--");

        //como ya tengo uno compilado comento esto
        //InputStream reporte = getClass().getResourceAsStream("/report2.jrxml"); //solo si hay que compilarlo
        InputStream reporte = getClass().getResourceAsStream("/operacion.jasper");

        //meto en memoria la operacion
        List<OperacionReportDTO> aux = new ArrayList<>();
        Operacion op = operacionService.findOne(id);
        LOGGER.debug("La operacion recuperada es " + op);
        aux.add(this.createReportDTO(op));

        try {
            //saco esto pues ya tengo compilado el reporte
            //JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
            //si quisiera grabar el reporte compilado
            //JRSaver.saveObject(jasperReport, "employeeReport.jasper");

            //Pido el reporte ya compilado (si tuviera uno y quiero recuperarlo
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reporte);

            // fills compiled report with parameters and a connection
            System.out.println("Empezando a llenar el reporte");

            //Puedo usar la conexion a la base de datos
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, ds.getConnection());
            //O puedo usar una coleccion en memoria
            JRBeanCollectionDataSource dsMemo = new JRBeanCollectionDataSource(aux);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dsMemo);

            System.out.println("el jasper print " + jasperPrint);
            // exports report to pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "pdf/report.pdf");
            System.out.println("empezandoa a exportar");

            /*
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            System.out.println("Tratando de abrir el output");

            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput("pdf/report.pdf"));
            System.out.println("");
            System.out.println("Pude abrir el output");
            SimplePdfReportConfiguration reportConfig
                    = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("baeldung");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            System.out.println("por generar el export");
            exporter.exportReport();
             */
            System.out.println("este es el expoerter ");
            getFile(response);
        } catch (Exception e) {

            System.out.println("salio por el catch hubo exceptcion " + e);
        }
        // compiles jrxml

    }

    @ModelAttribute("dataMaster")
    public DataMaster getDataMaster() {
        System.out.println("--Me meti en el data master--");
        return dataMaster;
    }

    private OperacionReportDTO createReportDTO(Operacion op) {
        OperacionReportDTO rDTO = new OperacionReportDTO();
        rDTO.setId(op.getId());
        rDTO.setFechaHora(op.getFechaHora().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        rDTO.setOperacion(op.getTipoOp().getValor());

        //recibido
        rDTO.setCajero(op.getCaja().getValor());
        rDTO.setMonedaR(op.getOpItemO().getMoneda().getValor());
        rDTO.setInstrumentoR(op.getOpItemO().getInstrumento().getValor());
        rDTO.setCantidadR(op.getOpItemO().getMonto());
        rDTO.setCotizacion(op.getValCotAplicado());

        //entregado
        rDTO.setCliNombre(op.getCliente().getNombre() + "," + op.getCliente().getApellido());
        rDTO.setCliTipoDoc("XXX");
        rDTO.setCliNumDoc(op.getCliente().getDocumento());
        rDTO.setNacionalidad("XXX");
        rDTO.setDomicilio("XXX");
        rDTO.setMonedaE(op.getOpItemD().getMoneda().getValor());
        rDTO.setInstrumentoE(op.getOpItemD().getInstrumento().getValor());
        rDTO.setCantidadE(op.getOpItemD().getMonto());
        return rDTO;
    }

}

package com.gestionit.base.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestionit.base.domain.Riesgo;

public class RiesgoExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Riesgo> riesgos;
     
    public RiesgoExcelExporter(List<Riesgo> riesgos) {
        this.riesgos = riesgos;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        int columnCount = 0; 
        
        createCell(row, columnCount++, "Codigo", style);      
        createCell(row, columnCount++, "Usuario Creador", style);  
        createCell(row, columnCount++, "Usuario Auditor", style);
        createCell(row, columnCount++, "Fecha", style);      
        createCell(row, columnCount++, "Origen", style); 
        createCell(row, columnCount++, "Tipo", style);      
        createCell(row, columnCount++, "Descripcion", style); 
        createCell(row, columnCount++, "Probabilidad de Ocurrencia", style); 
        createCell(row, columnCount++, "Riesgo Inherente Valor", style); 
        createCell(row, columnCount++, "Riesgo Inherente Descripcion", style); 
        createCell(row, columnCount++, "Salvaguarda Descripcion", style);
        createCell(row, columnCount++, "Afecta confidenciabilidad", style);
        createCell(row, columnCount++, "Afecta integridad", style);
        createCell(row, columnCount++, "Afecta disponibilidad", style);
        createCell(row, columnCount++, "Valor salvaguarda", style);
        createCell(row, columnCount++, "Impacto Final", style);
        createCell(row, columnCount++, "Probabilidad Final", style);
        createCell(row, columnCount++, "Riesgo Residual", style);
        createCell(row, columnCount++, "Codigo Formulario Aceptacion Riesgo", style);
        createCell(row, columnCount++, "Responsable", style);
        
        

    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if(value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if(value instanceof LocalDate){
        	cell.setCellValue((((LocalDate) value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }else {
        	cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Riesgo riesgo : riesgos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, riesgo.getId(), style);
            createCell(row, columnCount++, riesgo.getUsuarioCreador().getEmail(), style);
            createCell(row, columnCount++, riesgo.getUsuarioAuditor()==null?"":riesgo.getUsuarioAuditor().getEmail(), style);
            createCell(row, columnCount++, riesgo.getFechaAnalisis(), style);
            createCell(row, columnCount++, riesgo.getAmenaza().getOrigen().getOrigen(), style);
            createCell(row, columnCount++, riesgo.getAmenaza().getTipo(), style);
            createCell(row, columnCount++, riesgo.getDescripcion(), style);
            createCell(row, columnCount++, riesgo.getProbabilidadOcurrencia().getCalificacion(), style);
            createCell(row, columnCount++, riesgo.getRiesgoInherenteValor().getValor(), style);
            createCell(row, columnCount++, riesgo.getRiesgoInherenteValor().getRiesgoInherente().getCalificacion(), style);
            createCell(row, columnCount++, riesgo.getSalvaguarda().getDescripcion(), style);
            createCell(row, columnCount++, booleanToString(riesgo.getAfectaConfidencialidad()), style);
            createCell(row, columnCount++, booleanToString(riesgo.getAfectaIntegridad()), style);
            createCell(row, columnCount++, booleanToString(riesgo.getAfectaDisponibilidad()), style);
            createCell(row, columnCount++, riesgo.getSalvaguarda().getTipo().getDescripcion(), style);
            createCell(row, columnCount++, riesgo.getSalvaguarda().getImpactoFinal().getCalificacion(), style);
            createCell(row, columnCount++, riesgo.getSalvaguarda().getProbabilidadFinal().getCalificacion(), style);
            createCell(row, columnCount++, riesgo.getRiesgoResidualValor().getRiesgoResidual().getCalificacion(), style);
            createCell(row, columnCount++, riesgo.getCodigoFormulario(), style);     
            createCell(row, columnCount++, riesgo.getResponsable(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    
    
    private String booleanToString(Boolean afecta) {
    	return afecta?"SI":"NO";
    }
}

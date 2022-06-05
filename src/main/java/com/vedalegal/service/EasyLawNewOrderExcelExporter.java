package com.vedalegal.service;
 
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vedalegal.response.OrderVedaListResponse;
 
public class EasyLawNewOrderExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrderVedaListResponse> newOrders;    
    public EasyLawNewOrderExcelExporter(List<OrderVedaListResponse> newOrders) {
        this.newOrders = newOrders;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Easylaw Orders");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Serial No", style);      
        createCell(row, 1, "Order No", style); 
        createCell(row, 2, "User Name", style);    
        createCell(row, 3, "User Email", style);
        createCell(row, 4, "User Contact No", style);
        createCell(row, 5, "SubService Name", style);
        createCell(row, 6, "Assigned To", style);
        createCell(row, 7, "Status", style);
        createCell(row, 8, "Remarks", style);

         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
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
                 
        for (OrderVedaListResponse newOrders : newOrders) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row,columnCount++,rowCount-1,style);
            createCell(row, columnCount++, newOrders.getOrderNo(), style);
            createCell(row, columnCount++, newOrders.getUserName(), style);
            createCell(row, columnCount++, newOrders.getUserEmail(), style);
            createCell(row, columnCount++, newOrders.getUserContactNo().toString(), style);
            createCell(row, columnCount++, newOrders.getSubServiceName(), style);
            createCell(row, columnCount++, newOrders.getAssignedTo(), style);
            createCell(row, columnCount++, newOrders.getStatus().toString(), style);
            createCell(row, columnCount++, newOrders.getRemarks(), style);

             
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
}

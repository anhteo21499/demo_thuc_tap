package com.devpro.services.excel;

import com.devpro.entities.Products;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportExel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Products> customers;

    public ExportExel(List<Products> customers) {
        this.customers = customers;
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

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Create At", style);
        createCell(row, 2, "Update At", style);
        createCell(row, 3, "Create By", style);
        createCell(row, 4, "Update By", style);
        createCell(row, 5, "Status", style);

        createCell(row, 6, "Title", style);

        createCell(row, 7, "Short_description", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        if (value instanceof String)
        cell.setCellValue((String) value);

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Products product : customers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, product.getId(), style);
            createCell(row, columnCount++, product.getCreatedDate(), style);
            createCell(row, columnCount++, product.getUpdatedDate(), style);

            createCell(row, columnCount++, product.getCreatedBy(), style);
            createCell(row, columnCount++, product.getUpdatedBy(), style);
            createCell(row, columnCount++, product.getStatus(), style);

            createCell(row, columnCount++, product.getTitle(), style);

            createCell(row, columnCount++, product.getShort_description(), style);




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

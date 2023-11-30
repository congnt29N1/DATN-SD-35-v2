package com.example.datn.export;


import com.example.datn.entity.HoaTiet;
import com.example.datn.export.AbstractExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HoaTietExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public HoaTietExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Họa tiết");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "Material ID", cellStyle);
        createCell(row, 1, "Name", cellStyle);
        createCell(row, 2, "Description", cellStyle);
        createCell(row, 3, "Enabled", cellStyle);
    }

    private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public void export(List<HoaTiet> listHoaTiet, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","material_");

        writeHeaderLine();
        writeDataLines(listHoaTiet);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<HoaTiet> listHoaTiet) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (HoaTiet hoaTiet : listHoaTiet) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, hoaTiet.getIdHoaTiet(), cellStyle);
            createCell(row, columnIndex++, hoaTiet.getTenHoaTiet(), cellStyle);
            createCell(row, columnIndex++, hoaTiet.getMoTaHoaTiet(), cellStyle);
            createCell(row, columnIndex++, hoaTiet.isEnabled(), cellStyle);

        }
    }
}

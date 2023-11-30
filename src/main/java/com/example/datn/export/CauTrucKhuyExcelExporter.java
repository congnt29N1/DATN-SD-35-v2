
package com.example.datn.export;


import com.example.datn.entity.CauTrucKhuy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CauTrucKhuyExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public CauTrucKhuyExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cấu trúc khuy");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "CauTrucKhuy ID", cellStyle);
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

    public void export(List<CauTrucKhuy> listCauTrucKhuy, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","cautruckhuy_");

        writeHeaderLine();
        writeDataLines(listCauTrucKhuy);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<CauTrucKhuy> listCauTrucKhuy) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (CauTrucKhuy cauTrucKhuy : listCauTrucKhuy) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, cauTrucKhuy.getIdCauTrucKhuy(), cellStyle);
            createCell(row, columnIndex++, cauTrucKhuy.getTenCauTrucKhuy(), cellStyle);
            createCell(row, columnIndex++, cauTrucKhuy.getMoTaCauTrucKhuy(), cellStyle);
            createCell(row, columnIndex++, cauTrucKhuy.isEnabled(), cellStyle);

        }
    }
}

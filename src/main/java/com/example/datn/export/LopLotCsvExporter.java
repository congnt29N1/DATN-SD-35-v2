package com.example.datn.export;

import com.example.datn.entity.LopLot;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LopLotCsvExporter extends AbstractExporter{
    public void export(List<LopLot> listLopLot, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","loplot_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idLopLot","tenLopLot","moTaLopLot","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (LopLot listKieuTui: listLopLot){
            csvBeanWriter.write(listKieuTui,filedMapping);
        }
        csvBeanWriter.close();
    }
}

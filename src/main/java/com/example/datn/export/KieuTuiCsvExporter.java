package com.example.datn.export;

import com.example.datn.entity.KieuTui;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class KieuTuiCsvExporter extends AbstractExporter{
    public void export(List<KieuTui> listKieuTui, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idKieuTui","tenChatLieu","moTaChatLieu","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (KieuTui kieuTui: listKieuTui){
            csvBeanWriter.write(kieuTui,filedMapping);
        }
        csvBeanWriter.close();
    }
}

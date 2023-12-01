package com.example.datn.export;

import com.example.datn.entity.KieuDet;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class KieuDetCsvExporter extends AbstractExporter {
    public void export(List<KieuDet> listKieuDet, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idKieuDet","tenKieuDet","moTaKieuDet","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (KieuDet kieuDet: listKieuDet){
            csvBeanWriter.write(kieuDet,filedMapping);
        }
        csvBeanWriter.close();
    }
}

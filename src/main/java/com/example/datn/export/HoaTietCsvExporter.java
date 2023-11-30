package com.example.datn.export;


import com.example.datn.entity.HoaTiet;
import com.example.datn.export.AbstractExporter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HoaTietCsvExporter extends AbstractExporter {
    public void export(List<HoaTiet> listHoaTiet, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idHoaTiet","tenHoaTiet","moTaHoaTiet","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (HoaTiet hoaTiet: listHoaTiet){
            csvBeanWriter.write(hoaTiet,filedMapping);
        }
        csvBeanWriter.close();
    }
}

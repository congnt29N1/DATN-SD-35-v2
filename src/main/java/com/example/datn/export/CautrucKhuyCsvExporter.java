package com.example.datn.export;

import com.example.datn.entity.CauTrucKhuy;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CautrucKhuyCsvExporter extends AbstractExporter {
    public void export(List<CauTrucKhuy> listCauTrucKhuy, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","cautruckhuy_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idCauTrucKhuy","tenCauTrucKhuy","moTaCauTrucKhuy","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (CauTrucKhuy cauTrucKhuy: listCauTrucKhuy){
            csvBeanWriter.write(cauTrucKhuy,filedMapping);
        }
        csvBeanWriter.close();
    }
}

package com.example.datn.export;

import com.example.datn.entity.VeAo;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VeAoCsvExporter extends AbstractExporter {
    public void export(List<VeAo> listVeAo, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv","material_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ID","Name","Description","Enabled"};
        String[] filedMapping = {"idVatLieu","tenVatLieu","moTaVatLieu","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (VeAo veAo: listVeAo){
            csvBeanWriter.write(veAo,filedMapping);
        }
        csvBeanWriter.close();
    }

}

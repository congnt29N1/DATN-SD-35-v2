package com.example.datn.service.impl;

import com.example.datn.entity.DonHang;
import com.example.datn.entity.ReportItem;
import com.example.datn.entity.ReportType;
import com.example.datn.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MasterOrderReportService extends AbstractReportService {
    @Autowired private DonHangRepository repo;

    protected List<ReportItem> getReportDataByDateRangeInternal(Date startTime, Date endTime, ReportType reportType) {
        List<DonHang> combinedList1 = repo.findByOrderByStatusBetween(startTime, endTime, 3);
        List<DonHang> combinedList2 = repo.findByOrderByStatusBetween(startTime, endTime, 5);
        List<DonHang> listOrders = new ArrayList<>();
        listOrders.addAll(combinedList1);
        listOrders.addAll(combinedList2);
        printRawData(listOrders);
        System.out.println("Trạng thái: ");
        printRawDataStatus(listOrders);
        List<ReportItem> listReportItems = createReportData(startTime, endTime, reportType, 3);



        calculateSalesForReportData(listOrders, listReportItems);

        printReportData(listReportItems);

        return listReportItems;
    }


    private void calculateSalesForReportData(List<DonHang> listOrders, List<ReportItem> listReportItems){
        for(DonHang order: listOrders){
            String orderDateString = dateFormatter.format(order.getNgayTao());
            ReportItem reportItem = new ReportItem(orderDateString);
            int itemIndex = listReportItems.indexOf(reportItem);
            for (ReportItem item : listReportItems) {
                if (item.getGrossSales() == null || item.getNetSales() == null) {
                    item.setGrossSales(0.0);
                    item.setNetSales(0.0);
                }
            }
            if (itemIndex >=0){


                reportItem = listReportItems.get(itemIndex);
                reportItem.addNetSales(order.getTongTien());
                if( order.getPhiVanChuyen() != null) {
                    reportItem.addGrossSales(order.getTongTien() + order.getPhiVanChuyen());
                }else{
                    reportItem.addGrossSales(order.getTongTien());
                }
                reportItem.increaseOrderCount();
                reportItem.addStatus(order.getTrangThaiDonHang());
            }

        }
    }
    private void printReportData(List<ReportItem> listReportItems) {
        listReportItems.forEach(item ->{
            System.out.printf("%s, %.3f, %.3f, %d \n", item.getIdentifier(),
                    item.getGrossSales(), item.getNetSales(), item.getOrdersCount());
        });
    }

    private List<ReportItem> createReportData(Date startTime, Date endTime, ReportType reportType,
                                              Integer statusList) {
        List<ReportItem> listReportItems = new ArrayList<>();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        List<DonHang> combinedList1 = repo.findByOrderByStatusBetween(startTime, endTime, 3);
        List<DonHang> combinedList2 = repo.findByOrderByStatusBetween(startTime, endTime, 5);
        List<DonHang> listOrders = new ArrayList<>();
        listOrders.addAll(combinedList1);
        listOrders.addAll(combinedList2);

        do {
            String dateString = dateFormatter.format(startDate.getTime());

            // Check if any DonHang status is in the statusList
            boolean hasMatchingStatus = false;
            for (DonHang order : listOrders) {
                if (statusList == 3 || statusList == 5) {
                    hasMatchingStatus = true;
                    break;
                }
            }

//             Add a ReportItem only if there's a DonHang with matching status
            if (hasMatchingStatus) {
                listReportItems.add(new ReportItem(dateString));
            }
            if (reportType.equals(ReportType.DAY)) {
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            } else if (reportType.equals(ReportType.MONTH)) {
                startDate.add(Calendar.MONTH, 1);
            }
        } while (startDate.before(endDate));
        return listReportItems;
    }

    private void printRawData(List<DonHang> listOrders){
        listOrders.forEach(order ->{
            System.out.printf("%-3d | %s | %.3f | %.3f \n",
                    order.getIdDonHang(),
                    order.getNgayTao(),
                    order.getTongTien(),
                    order.getPhiVanChuyen());
        });
    }

    private void printRawDataStatus(List<DonHang> listOrders){
        listOrders.forEach(order ->{
            System.out.printf("%-3d | %s | %.3f | %.3f | %-3d\n",
                    order.getIdDonHang(),
                    order.getNgayTao(),
                    order.getTongTien(),
                    order.getPhiVanChuyen(),
                    order.getTrangThaiDonHang());
        });
    }

}

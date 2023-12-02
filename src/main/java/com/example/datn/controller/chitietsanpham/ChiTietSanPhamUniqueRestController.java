package com.example.datn.controller.chitietsanpham;

import com.example.datn.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChiTietSanPhamUniqueRestController {
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/productDetails/check_details_unique")
    public String checkUniqueDetails(
            @RequestParam String maChiTietSanPham,
            @RequestParam String tenSanPham,
            @RequestParam String tenMauSac,
            @RequestParam String tenChatLieu,
            @RequestParam String tenKichCo,
            @RequestParam String tenCauTrucKhuy,
            @RequestParam String tenHoaTiet,
            @RequestParam String tenKieuTui,
            @RequestParam String tenKieuDet,
            @RequestParam String tenLopLot,
            @RequestParam String tenVeAo,
            @RequestParam String tenXeTa
    ) {
        boolean isUnique = chiTietSanPhamService.isUniqueChiTietSanPham(
                maChiTietSanPham,tenSanPham,tenMauSac,tenChatLieu,tenKichCo,tenCauTrucKhuy,
                tenHoaTiet,tenKieuTui,tenKieuDet,tenLopLot,tenVeAo,tenXeTa);
        if (isUnique) {
            return "OK";
        } else {
            return "Duplicated";
        }
    }


    @PostMapping("/admin/productDetails/check_details_unique_update")
    public String checkUniqueDetailsUpdate(
            @RequestParam Integer idChiTietSanPham,
            @RequestParam String maChiTietSanPham,
            @RequestParam String tenSanPham,
            @RequestParam String tenMauSac,
            @RequestParam String tenChatLieu,
            @RequestParam String tenKichCo,
            @RequestParam String tenCauTrucKhuy,
            @RequestParam String tenHoaTiet,
            @RequestParam String tenKieuTui,
            @RequestParam String tenKieuDet,
            @RequestParam String tenLopLot,
            @RequestParam String tenVeAo,
            @RequestParam String tenXeTa
    ) {
        boolean isUnique = chiTietSanPhamService.isUniqueChiTietSanPhamUpdate( idChiTietSanPham,maChiTietSanPham,
                tenSanPham,tenMauSac,tenChatLieu,tenKichCo,tenCauTrucKhuy,tenHoaTiet,
                tenKieuTui,tenKieuDet,tenLopLot,tenVeAo,tenXeTa
        );
        if (isUnique) {
            return "OK";
        } else {
            return "Duplicated";
        }
    }
}

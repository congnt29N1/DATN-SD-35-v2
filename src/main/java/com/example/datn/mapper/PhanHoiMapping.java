package com.example.datn.mapper;

import com.example.datn.entity.PhanHoi;
import com.example.datn.response.PhanHoiResponse;
import lombok.Data;

@Data
public class PhanHoiMapping {


    public  static PhanHoiResponse mapEntitytoResponse(PhanHoi phanHoi){
    PhanHoiResponse phanHoiResponse =  PhanHoiResponse.builder()
            .idPhanHoi(phanHoi.getIdPhanHoi())
            .chiTietSanPham(phanHoi.getChiTietSanPham())
            .danhGia(phanHoi.getDanhGia())
            .ghiChu(phanHoi.getGhiChu())
            .noiDungPhanHoi(phanHoi.getNoiDungPhanHoi())
            .khachHang(phanHoi.getKhachHang())
            .ngaySua(phanHoi.getNgaySua())
            .thoiGianPhanHoi(phanHoi.getThoiGianPhanHoi())
            .trangThaiPhanHoi(phanHoi.getTrangThaiPhanHoi())
            .ngayTao(phanHoi.getNgayTao())
            .build();
        return phanHoiResponse;
}




}

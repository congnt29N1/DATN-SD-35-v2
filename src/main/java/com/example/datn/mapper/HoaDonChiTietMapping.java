package com.example.datn.mapper;

import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.response.HoaDonChiTietResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoaDonChiTietMapping {
    public  static HoaDonChiTietResponse mapEntitytoResponse(HoaDonChiTiet hoaDonChiTiet){
        HoaDonChiTietResponse hoaDonChiTietResponse =  HoaDonChiTietResponse.builder()
                .idHoaDonChiTiet(hoaDonChiTiet.getIdHoaDonChiTiet())
                .chiTietSanPham(hoaDonChiTiet.getChiTietSanPham())
                .donHang(hoaDonChiTiet.getDonHang())
                .giaBan(hoaDonChiTiet.getGiaBan())
                .soLuong(hoaDonChiTiet.getSoLuong())

                .build();
        return hoaDonChiTietResponse;
    }
}

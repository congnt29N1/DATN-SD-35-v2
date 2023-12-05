package com.example.datn.service;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.request.HoaDonChiTietRequest;

import java.util.List;

public interface HoaDonChiTietService {
    HoaDonChiTiet save(HoaDonChiTiet hdct);


    List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang);

    List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT);

    Double getTongGia(List<HoaDonChiTietRequest> list);

    List<HoaDonChiTiet> getByIdDonHang(int id);

    List<HoaDonChiTiet> getByHoaDonId(DonHang donHang);

    public List<HoaDonChiTiet> getHDCTByMaDonHang(String maDonHang);

    void themSoLuongSanPham(
            int soLuong,
            ChiTietSanPham chiTietSanPham,
            DonHang donHang
    );

    void xoaHDCT(
            HoaDonChiTiet hoaDonChiTiet
    );

    HoaDonChiTiet findHoaDonChiTietById(
            int id
    );

    void xoaHDCTByIdDonHang(DonHang donHang);
}

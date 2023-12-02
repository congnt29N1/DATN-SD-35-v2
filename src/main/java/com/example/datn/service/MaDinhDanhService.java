package com.example.datn.service;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.MaDinhDanhCTSP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaDinhDanhService {

    MaDinhDanhCTSP save(MaDinhDanhCTSP maDinhDanhCTSP);
    List<MaDinhDanhCTSP> saveMany(List<MaDinhDanhCTSP> maDinhDanhCTSP);
    List<MaDinhDanhCTSP> getALl();
    MaDinhDanhCTSP get(Integer id);
    Page<MaDinhDanhCTSP> searchSeri(int pageNumber, int pageSize, String keyword);

    Integer countMaDinhDanh (Integer idChiTietSanPham);
    List<MaDinhDanhCTSP> findByChiTietSanPham(ChiTietSanPham chiTietSanPham, Integer soLuong);

    void updateSoLuongAdmin(int idHDCT, int soLuongCapNhat);
    List<MaDinhDanhCTSP> findByHoaDonChiTiet (Integer idhoaDonChiTiet);
}

package com.example.datn.service;

import com.example.datn.request.PhanHoiRequest;
import com.example.datn.response.PhanHoiResponse;

import java.util.List;

public interface PhanHoiService {

    List<PhanHoiResponse> findAll(Integer idSanPham);

    boolean checkPhanHoi(Integer idKhachHang, Integer idSanPham);

    PhanHoiResponse addPhanHoi(PhanHoiRequest phanHoiRequest);

    Long countPH(Integer idKhachHang, Integer idChiTietSanPham);
    Long countHDCT(Integer idKhachHang, Integer idChiTietSanPham);

}

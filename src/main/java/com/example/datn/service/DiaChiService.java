package com.example.datn.service;

import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.request.DiaChiRequest;
import com.example.datn.response.DiaChiResponse;

import java.util.List;

public interface DiaChiService {
    public DiaChiResponse createDiaChi(Integer idKhachHang,DiaChiRequest diaChiRequest) throws Exception;
    public List<DiaChi> getAllDiaChi();
    List<DiaChi> getAllDiaChiByKhachHang(KhachHang khachHang);

    List<DiaChiResponse> getDiaChiByKhachHang(Integer idKhachHang) throws Exception;
    DiaChiResponse updateDC(Integer idDiachi, DiaChiRequest diaChiRequest) throws Exception;
    DiaChiResponse updateDCDefault(Integer idKhachHang,Integer idDiachi) throws Exception;
    void delete(Integer idDiachi) ;

    String getDiaChiCuThe(DiaChiRequest diaChiRequest) throws Exception;
}

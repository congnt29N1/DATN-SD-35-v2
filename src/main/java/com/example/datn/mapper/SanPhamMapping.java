package com.example.datn.mapper;


import com.example.datn.entity.SanPham;
import com.example.datn.response.SanPhamDetailResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPhamMapping {
    public  static SanPhamDetailResponse mapEntitytoResponse(SanPham sp){
        SanPhamDetailResponse sanPhamDetailResponse =  SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .listAnhSanPham(sp.getListAnhSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .trangThai(sp.getTrangThai())
                .tenSanPham(sp.getTenSanPham())
                .maSanPham(sp.getMaSanPham())
                .build();
        return sanPhamDetailResponse;
    }
}

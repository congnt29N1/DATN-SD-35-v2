package com.example.datn.response;

import com.example.datn.entity.AnhSanPham;
import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DanhMuc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamDetailResponse {
    private Integer idSanPham;
    private DanhMuc danhMuc;
    private List<AnhSanPham> listAnhSanPham;
    private String tenSanPham;
    private String maSanPham;
    private String moTaSanPham;
    private Double giaSanPham;
    private Integer trangThai;
    private List<ChiTietSanPham> listChiTietSanPham;
}

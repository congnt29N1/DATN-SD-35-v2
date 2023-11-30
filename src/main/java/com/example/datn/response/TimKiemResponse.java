package com.example.datn.response;

import com.example.datn.entity.AnhSanPham;
import com.example.datn.entity.ChiTietSanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TimKiemResponse {
    private Integer sanPhamID;
    private String tenSanPham;
    private Double giaSanPham;
    private List<AnhSanPham> listAnhSanPham;
    private List<ChiTietSanPham> listChiTietSanPham ;
}

package com.example.datn.response;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DonHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonChiTietResponse {
    private Integer idHoaDonChiTiet;

    private DonHang donHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuong;

    private Double giaBan;
}

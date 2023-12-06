package com.example.datn.request;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.GioHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietGioHangRequest {
    private Integer idChiTietGioHang;

    private GioHang gioHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuongSanPham;

    private Double giaBan;

    private Date ngayTao;

    private String ghiChu;
}

package com.example.datn.response;

import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.entity.ChatLieu;
import com.example.datn.entity.HoaTiet;
import com.example.datn.entity.KhuyenMai;
import com.example.datn.entity.KichCo;
import com.example.datn.entity.KieuDet;
import com.example.datn.entity.KieuTui;
import com.example.datn.entity.LopLot;
import com.example.datn.entity.MauSac;
import com.example.datn.entity.SanPham;
import com.example.datn.entity.VeAo;
import com.example.datn.entity.XeTa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamAdminResponse {
    private Integer idChiTietSanPham;
    private String maChiTietSanPham;
    private SanPham sanPham;
    private KhuyenMai khuyenMai;
    private MauSac mauSac;
    private Double giaSanPham;
    private Integer soLuong;
    private ChatLieu chatLieu;
    private CauTrucKhuy cauTrucKhuy;
    private HoaTiet hoaTiet;
    private KichCo kichCo;
    private KieuDet kieuDet;
    private KieuTui kieuTui;
    private LopLot lopLot;
    private VeAo veAo;
    private XeTa xeTa;
}

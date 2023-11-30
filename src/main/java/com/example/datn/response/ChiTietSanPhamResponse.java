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
import com.example.datn.entity.PhanHoi;
import com.example.datn.entity.SanPham;
import com.example.datn.entity.VeAo;
import com.example.datn.entity.XeTa;
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
public class ChiTietSanPhamResponse {
    private Integer idChiTietSanPham;
    private SanPham sanPham;
    private KhuyenMai khuyenMai;
    private MauSac mauSac;
    private ChatLieu chatLieu;
    private KichCo kichCo;
    private CauTrucKhuy cauTrucKhuy;
    private HoaTiet hoaTiet;
    private KieuTui kieuTui;
    private KieuDet kieuDet;
    private LopLot lopLot;
    private VeAo veAo;
    private XeTa xeTa;
    private String soMiPhuHop;
    private String giayPhuHop;
    private Integer trangThai;
    private Double giaSanPham;
    private Integer soLuong;
    private List<PhanHoi> listPhanHoi ;
}

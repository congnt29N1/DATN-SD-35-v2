package com.example.datn.mapper;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.response.ChiTietSanPhamResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChiTietSanPhamMapping {
    public  static ChiTietSanPhamResponse mapEntitytoResponse(ChiTietSanPham sp){
        ChiTietSanPhamResponse chiTietSanPhamResponse =  ChiTietSanPhamResponse.builder()
                .idChiTietSanPham(sp.getIdChiTietSanPham())
                .sanPham(sp.getSanPham())
                .khuyenMai(sp.getKhuyenMai())
                .mauSac(sp.getMauSac())
                .chatLieu(sp.getChatLieu())
                .kichCo(sp.getKichCo())
                .cauTrucKhuy(sp.getCauTrucKhuy())
                .hoaTiet(sp.getHoaTiet())
                .kieuTui(sp.getKieuTui())
                .kieuDet(sp.getKieuDet())
                .lopLot(sp.getLopLot())
                .veAo(sp.getVeAo())
                .xeTa(sp.getXeTa())
                .soMiPhuHop(sp.getSoMiPhuHop())
                .giayPhuHop(sp.getGiayPhuHop())
                .trangThai(sp.getTrangThai())
                .giaSanPham(sp.getGiaSanPham())
//                .soLuong(sp.getSoLuong())
                .listPhanHoi(sp.getListPhanHoi())
                .build();
        return chiTietSanPhamResponse;
    }
}

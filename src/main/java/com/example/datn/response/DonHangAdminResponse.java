package com.example.datn.response;

import com.example.datn.entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangAdminResponse {
    private Integer idDonHang;
    private String maDonHang;
    private Integer idNhanVien;
    private KhachHang khachHang;
    private String ngayTao;
    private String ngayCapNhap;
    private Integer trangThaiDonHang;
    private String diaChi;
    private Double tongTien;
    private Double phiVanChuyen;
    private String ghiChu;
    private String lyDo;

}

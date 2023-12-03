package com.example.datn.response;

import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangResponse {
    private Integer idDonHang;

    private String maDonHang;

    private NhanVien nhanVien;

    private KhachHang khachHang;

    private Date ngayTao;

    private Date ngayGiaoHang;

    private Double tongTien;

    private Integer trangThaiDonHang;

    private String TinhThanh;

    private String QuanHuyen;

    private String PhuongXa;

    private String diaChi;

    private Double phiVanChuyen;

    private  String ngayCapNhap;

    private String ghiChu;
    private List<HoaDonChiTiet> hoaDonChiTiets;
    private String lyDo;
}

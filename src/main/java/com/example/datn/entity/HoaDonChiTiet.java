package com.example.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {
    @Id
    @Column(name = "id_hoa_don_chi_tiet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHoaDonChiTiet;

    @ManyToOne
    @JoinColumn(name = "id_don_hang")
    @JsonIgnore
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "id_chi_tiet_san_pham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_ban")
    private Double giaBan;

    public HoaDonChiTiet(Integer idHoaDonChiTiet) {
        this.idHoaDonChiTiet = idHoaDonChiTiet;
    }
    @Column(name = "chiet_khau")
    private Integer chietKhau;

    public HoaDonChiTiet(String tenDanhMuc, Integer soLuong, Double giaBan, Double phiVanChuyen) {
        this.chiTietSanPham = new ChiTietSanPham();
        this.chiTietSanPham.setSanPham(new SanPham());
        this.chiTietSanPham.getSanPham().setDanhMuc(new DanhMuc(tenDanhMuc));
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.setDonHang(new DonHang(phiVanChuyen));
    }

    public HoaDonChiTiet(Integer soLuong, String tenSanPham, Double giaBan, Double phiVanChuyen) {
        this.soLuong = soLuong;
        this.chiTietSanPham = new ChiTietSanPham();
        this.chiTietSanPham.setSanPham(new SanPham(tenSanPham));
        this.giaBan = giaBan;
        this.setDonHang(new DonHang(phiVanChuyen));
    }

    public HoaDonChiTiet(Integer idHoaDonChiTiet, Integer soLuong, Double giaBan, Double phiVanChuyen) {
        this.idHoaDonChiTiet = idHoaDonChiTiet;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.setDonHang(new DonHang(phiVanChuyen));
    }

    public HoaDonChiTiet(ChiTietSanPham chiTietSanPham) {
        this.chiTietSanPham = chiTietSanPham;
    }

    public HoaDonChiTiet(DonHang donHang) {
        this.donHang = donHang;
    }
}
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
public class DiaChiResponse {
    private Integer idDiaChi;
    private Integer idTinhThanh;
    private Integer idQuanHuyen;
    private String idPhuongXa;
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String diaChi;
    private Integer maBuuChinh;
    private String soDienThoai;
    private String ghiChu;
    private Integer trangThaiMacDinh;
    private KhachHang khachHang;
}

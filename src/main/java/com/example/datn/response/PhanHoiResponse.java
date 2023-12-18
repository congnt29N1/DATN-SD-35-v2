package com.example.datn.response;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhanHoiResponse {
    private Integer idPhanHoi;

    private KhachHang khachHang;

    private ChiTietSanPham chiTietSanPham;

    private String noiDungPhanHoi;

    private Timestamp thoiGianPhanHoi;

    private Integer trangThaiPhanHoi;

    private Integer danhGia;

    private String ghiChu;

    private Date ngayTao;

    private Date ngaySua;

}

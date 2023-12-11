package com.example.datn.response;

import com.example.datn.entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiohangResponse {
    private Integer idGioHang;

    private KhachHang khachHang;

    private Date ngayTaoGioHang;

    private Integer trangThaiGioHang;

    private String ghiChu;

    private Timestamp thoiGianCapNhapGioHang;
}

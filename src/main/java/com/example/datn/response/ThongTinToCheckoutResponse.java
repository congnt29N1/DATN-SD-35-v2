package com.example.datn.response;

import com.example.datn.entity.DiaChi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ThongTinToCheckoutResponse {
    private Integer id;
    private String tenKhachHang;
    private String soDienThoai;
    private List<DiaChi> listDiaChi;
}

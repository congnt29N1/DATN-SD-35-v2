package com.example.datn.service;

import com.example.datn.request.GioHangRequest;
import com.example.datn.response.GiohangResponse;

public interface GioHangService {
    GiohangResponse addGioHang(GioHangRequest gioHangRequest);

    GiohangResponse findGioHang(Integer idKhachHang);
}

package com.example.datn.service.impl;

import com.example.datn.entity.GioHang;
import com.example.datn.mapper.GioHangMapping;
import com.example.datn.repository.GioHangRepository;
import com.example.datn.request.GioHangRequest;
import com.example.datn.response.GiohangResponse;
import com.example.datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    GioHangRepository gioHangRepository;
    @Override
    public GiohangResponse addGioHang(GioHangRequest gioHangRequest) {
        GioHang gioHang = GioHangMapping.mapRequestToEntity(gioHangRequest);
        GiohangResponse gioHangResponse = GioHangMapping.mapEntitytoResponse(gioHangRepository.save(gioHang));
        return gioHangResponse;
    }

    @Override
    public GiohangResponse findGioHang(Integer idKhachHang) {
        //find khachHang 1
        GioHang gioHang = gioHangRepository.findGioHang(1);
        GiohangResponse gioHangResponse = GioHangMapping.mapEntitytoResponse(gioHang);
        return gioHangResponse;
    }
}

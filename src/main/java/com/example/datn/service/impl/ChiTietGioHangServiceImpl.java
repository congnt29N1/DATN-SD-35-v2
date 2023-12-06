package com.example.datn.service.impl;

import com.example.datn.entity.ChiTietGioHang;
import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.GioHang;
import com.example.datn.entity.KhachHang;
import com.example.datn.mapper.ChiTietGioHangMapping;
import com.example.datn.repository.ChiTietGioHangRepository;
import com.example.datn.repository.ChiTietSanPhamRepository;
import com.example.datn.repository.GioHangRepository;
import com.example.datn.repository.KhachHangRepository;
import com.example.datn.request.CartRequest;
import com.example.datn.response.ChiTietGioHangResponse;
import com.example.datn.service.ChiTietGioHangService;
import com.example.datn.service.MaDinhDanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    @Autowired
    ChiTietGioHangRepository chiTietGioHangRepository;

    @Autowired
    MaDinhDanhService maDinhDanhService;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Override
    public List<ChiTietGioHangResponse> getChiTietGioHang(Integer idKhachhnag) {
        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.giohangChiTiet(idKhachhnag);
        for (ChiTietGioHang chiTietGioHang: chiTietGioHangList  ) {
            Integer countSeri = maDinhDanhService.countMaDinhDanh(chiTietGioHang.getChiTietSanPham().getIdChiTietSanPham());
            if(chiTietGioHang.getSoLuongSanPham() > countSeri){
                chiTietGioHang.setSoLuongSanPham(countSeri);
            }
        }
        List<ChiTietGioHangResponse> responseList = chiTietGioHangList.stream().map(ChiTietGioHangMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public ChiTietGioHangResponse update(Integer soLuong, Integer idChiTietGioHang) throws Exception {
        try {
            ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(idChiTietGioHang).get();
            chiTietGioHang.setSoLuongSanPham(soLuong);
            ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
            return chiTietGioHangResponse;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        chiTietGioHangRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chiTietGioHangRepository.deleteAll();
    }

    @Override
    public ChiTietGioHangResponse add(CartRequest cartRequest) {
        KhachHang khachHang = khachHangRepository.findById(cartRequest.getIdKhachHang()).get();
        GioHang gioHang = GioHang.builder()
                .idGioHang(null)
                .ngayTaoGioHang(new Date())
                .trangThaiGioHang(1)
                .khachHang(khachHang)
                .thoiGianCapNhapGioHang(new Timestamp(new Date().getTime()))
                .build();
        gioHangRepository.save(gioHang);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(cartRequest.getIdChiTietSanPham()).get();
        if(maDinhDanhService.countMaDinhDanh(cartRequest.getIdChiTietSanPham())< cartRequest.getSoLuong()){
            return null ;
        }
        ChiTietGioHang chiTietGioHang = ChiTietGioHang.builder()
                .idChiTietGioHang(1)
                .chiTietSanPham(chiTietSanPham)
                .gioHang(gioHang)
                .ghiChu("")
                .giaBan(cartRequest.getGiaSanPham())
                .ngayTao(new Date())
                .soLuongSanPham(cartRequest.getSoLuong())
                .build();
        ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
        return chiTietGioHangResponse;
    }

    @Override
    public ChiTietGioHangResponse addToCart(CartRequest cartRequest) {
        try {
            GioHang gioHang = gioHangRepository.findGioHang(cartRequest.getIdKhachHang());
            if (gioHang == null) {
                return add(cartRequest);
            } else {
                ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(cartRequest.getIdChiTietSanPham()).get();
                ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findChiTietGioHangByCTSP(cartRequest.getIdChiTietSanPham(), cartRequest.getIdKhachHang());
                if (chiTietGioHang == null) {

                    ChiTietGioHang chiTietGioHang1 = ChiTietGioHang.builder()
                            .idChiTietGioHang(null)
                            .chiTietSanPham(chiTietSanPham)
                            .gioHang(gioHang)
                            .ghiChu("")
                            .giaBan(cartRequest.getGiaSanPham())
                            .ngayTao(new Date())
                            .soLuongSanPham(cartRequest.getSoLuong())
                            .build();
                    if(maDinhDanhService.countMaDinhDanh(cartRequest.getIdChiTietSanPham()) < cartRequest.getSoLuong()){
                        return null ;
                    }
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang1));
                    return chiTietGioHangResponse;
                } else {
                    if(maDinhDanhService.countMaDinhDanh(cartRequest.getIdChiTietSanPham()) < chiTietGioHang.getSoLuongSanPham()+ cartRequest.getSoLuong()){
                        return  null ;
                    }else{
                        chiTietGioHang.setSoLuongSanPham(chiTietGioHang.getSoLuongSanPham()+ cartRequest.getSoLuong());
                    }
                    chiTietGioHang.setGiaBan(cartRequest.getGiaSanPham());
                    chiTietGioHang.setChiTietSanPham(chiTietSanPham);
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
                    return chiTietGioHangResponse;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lỗi");
            return null;
        }
    }

    @Override
    public void removeByCTSPAndKhachHang(Integer idKhachHang, HashMap<Integer, Integer> idChiTietSanPhamAndSoLuong) {
        List<ChiTietGioHang> listChiTietGioHang = new ArrayList<>();
        idChiTietSanPhamAndSoLuong.forEach((id,soLuong)->{
            ChiTietGioHang ctgh = chiTietGioHangRepository.findChiTietGioHangByCTSPVaKhachHang(id,soLuong,idKhachHang);
            if(ctgh != null) {
                listChiTietGioHang.add(ctgh);
            }
        });
        chiTietGioHangRepository.deleteAll(listChiTietGioHang);
    }
}

package com.example.datn.service;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.exception.ChiTietSanPhamNotFountException;
import com.example.datn.response.SanPhamAdminResponse;
import com.example.datn.response.TimKiemSettingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChiTietSanPhamService {
    public static final int PRODUCT_DETAIL_PER_PAGE = 3;
    int totalPageSearchSP(String key, int pageNum);
    List<SanPhamAdminResponse> searchSP(String key, int pageNum);
    TimKiemSettingResponse getTimKiemSetting ();
    ChiTietSanPham getChiTietSanPhamById(Integer id);
    ChiTietSanPham update(ChiTietSanPham chiTietSanPham);
    Page<ChiTietSanPham> findByMaSP(String maSanPham, int pageNum);

    Page<ChiTietSanPham> getALlChiTietSanPhamPage(int pageNum);

    List<SanPhamAdminResponse> getAllSanPhamAminResponse(int pageNum);

    ChiTietSanPham findByMaChiTietSanPham(String maChimaTietSanPham);
    ChiTietSanPham getChiTietSanPhamByMa (String ma);

    public List<ChiTietSanPham> listAll();
    public boolean isUniqueChiTietSanPham(
            String maChiTietSanPham,
            String tenSanPham,
            String tenMauSac,
            String tenChatLieu,
            String tenKichCo,
            String tenCauTrucKhuy,
            String tenHoaTiet,
            String tenKieuTui,
            String tenKieuDet,
            String tenLopLot,
            String tenXeTa,
            String tenVeAo
    );
    public boolean isUniqueChiTietSanPhamUpdate(
            Integer idChiTietSanPham,
            String maChiTietSanPham,
            String tenSanPham,
            String tenMauSac,
            String tenChatLieu,
            String tenKichCo,
            String tenCauTrucKhuy,
            String tenHoaTiet,
            String tenKieuTui,
            String tenKieuDet,
            String tenLopLot,
            String tenVeAo,
            String tenXeTa
    );
    public ChiTietSanPham save(ChiTietSanPham chiTietSanPham);

    public ChiTietSanPham get(Integer id) throws ChiTietSanPhamNotFountException;

    Page<ChiTietSanPham> listByPageAndProductName(int pageNum, String sortField, String sortDir, String keyword, String productName);

    List<ChiTietSanPham> findByIdSp(Integer IdSP);
}

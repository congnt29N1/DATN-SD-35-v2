package com.example.datn.service;

import com.example.datn.entity.SanPham;
import com.example.datn.exception.SanPhamNotFountException;
import com.example.datn.request.TimKiemRequest;
import com.example.datn.response.ChiTietSanPhamResponse;
import com.example.datn.response.SanPhamDetailResponse;
import com.example.datn.response.TimKiemResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface SanPhamService {
    public static final int PRODUCT_PER_PAGE = 5;

    Set<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest);

    List<SanPhamDetailResponse> getSPnew();
    List<SanPhamDetailResponse> getSPFeature();

    List<SanPhamDetailResponse> getSPchay();

    SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId);

    List<ChiTietSanPhamResponse> getSPchayKM(Integer idChiTietSanPham);
    List<SanPham> listAll();

    SanPham save(SanPham sanPham);
    SanPham get(Integer id) throws SanPhamNotFountException;

    Page<SanPham> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean checkUniqueTenAndMa(String ten, String ma);

    boolean checkUniqueTenMaId(String ten, String ma, Integer id);
}

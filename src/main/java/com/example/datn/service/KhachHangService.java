package com.example.datn.service;

import com.example.datn.entity.KhachHang;
import com.example.datn.exception.CustomException.KhachHangNotFoundException;
import com.example.datn.response.ThongTinCaNhanResponse;
import com.example.datn.response.ThongTinToCheckoutResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KhachHangService {
    public static final int CUSTOMERS_PER_PAGE = 4;
    public KhachHang findKhachHangById(Integer id);

    KhachHang get(Integer id) throws KhachHangNotFoundException, Exception;

    public KhachHang updateThongTinCaNhan(Integer idKhachhang, ThongTinCaNhanResponse thongTinCaNhanResponse);
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id);
    public ThongTinToCheckoutResponse getThongTinToCheckout(Integer id);

    public void saveKhachHang(KhachHang khachHang);

    List<KhachHang> getAllKhachHang();

    List<KhachHang> getAllPaginationVatLieu();

    Page<KhachHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean checkUnique(Integer id, String email, String soDT);

//    void updateKhachHangEnabledStatus(Integer id, boolean enabled);
  
    KhachHang findByPhoneNumber(String phoneNumber);

}

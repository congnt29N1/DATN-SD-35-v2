package com.example.datn.service;

import com.example.datn.entity.DonHang;
import com.example.datn.request.DonHangRequest;
import com.example.datn.response.DonHangResponse;
import com.example.datn.response.HoaDonChiTietResponse;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface DonHangService {
    public static final int DONHANG_PAGE = 10;
    DonHang save(DonHang donHang);

    DonHang getById(Integer idDonHang);

    public Page<DonHang> getAll(int pageNumber);

    List<DonHang> findByNgayTao(Date dateStart, Date dateEnd, Integer status);

    void updateTongTienAdmin(int id);

    List<DonHang> findDonHangChuaThanhToan();

    public Double tongTien(int id);

    public DonHang findById(int id);

    public void updateTrangThaiDonHang(DonHang donHang);

    List<HoaDonChiTietResponse> findHDCTbyDH(Integer idDonhang);

    List<DonHangResponse> findAllHD(Integer idKhachHang) throws Exception;

    List<DonHangResponse> findHDByStatus(Integer idKhachHang, Integer trangThaiDonHang) throws Exception;

    public DonHangResponse updateDH(DonHangRequest donHangRequest);

    List<DonHang> findByTrangThaiDonHang(int trangThai);

    Page<DonHang> getAllForBanHang(int pageNum);

    public DonHang findByMaDonHang(String maDonHang);

    String thanhToanAdmin(DonHang donHang);

    String xoaDonHangAdmin(DonHang donHang);

    void xoaDonHang(DonHang donhang);

    List<DonHang> getAllDonHang();

    List<DonHang> getAllPaginationDonHang();

    Page<DonHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword);
    Page<DonHang> listByPageStatus(int pageNumber, String sortField, String sortDir, String keyword, int status);

    Integer countDHbyStatus(Integer trangThaiDonhang);
    Integer countDHAll();

    Integer soLuongImeiCoTheCapNhat(int idHDCT);
}

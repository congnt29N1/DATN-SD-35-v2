package com.example.datn.service.impl;

import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.mapper.DonHangMapping;
import com.example.datn.mapper.HoaDonChiTietMapping;
import com.example.datn.repository.ChiTietSanPhamRepository;
import com.example.datn.repository.DonHangRepository;
import com.example.datn.repository.HoaDonChiTietRepository;
import com.example.datn.repository.MaDinhDanhRepository;
import com.example.datn.request.DonHangRequest;
import com.example.datn.response.DonHangResponse;
import com.example.datn.response.HoaDonChiTietResponse;
import com.example.datn.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonHangServiceImpl implements DonHangService {
    public static final int DONHANG_PAGE = 10;
    @Autowired
    DonHangRepository donHangRepository;
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    MaDinhDanhRepository maDinhDanhRepository;

    @Override
    public DonHang save(DonHang donHang) {
        return donHangRepository.save(donHang);
    }

    @Override
    public DonHang getById(Integer idDonHang) {
        Optional<DonHang> optionalDonHang = donHangRepository.findById(idDonHang);
        if (optionalDonHang.isPresent()) {
            return optionalDonHang.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<DonHang> getAll(int pageNumber) {
        Page<DonHang> allDonHang = donHangRepository.findAll(PageRequest.of(pageNumber - 1, DONHANG_PAGE));
        return allDonHang;
    }

    @Override
    public List<DonHang> findByNgayTao(Date dateStart, Date dateEnd, Integer status) {
        List<DonHang> allDonHang = donHangRepository.findByNgayTao(dateStart, dateEnd, status);
        return allDonHang;
    }

    @Override
    public void updateTongTienAdmin(int id) {
        DonHang donHang = donHangRepository.findByIdDonHang(id);

        Double tongTien = donHangRepository.tongTienAdmin(id);

        donHangRepository.updateTongTienAdmin(id, tongTien);
    }

    @Override
    public List<DonHang> findDonHangChuaThanhToan() {
        return donHangRepository.findDonHangChuaThanhToan();
    }

    @Override
    public Double tongTien(int id) {
        return donHangRepository.findTongTienByIdDonHang(id);
    }

    @Override
    public DonHang findById(int id) {
        return donHangRepository.findByIdDonHang(id);
    }

    @Override
    public void updateTrangThaiDonHang(DonHang donHang) {
        donHangRepository.updateTrangThaiDonHang(donHang);
    }

    @Override
    public List<HoaDonChiTietResponse> findHDCTbyDH(Integer idDonhang) {
        List<HoaDonChiTiet> listHDCT = donHangRepository.findHDCTbyDH(idDonhang);
        List<HoaDonChiTietResponse> responseList = listHDCT.stream().map(HoaDonChiTietMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<DonHangResponse> findAllHD(Integer idKhachHang) throws Exception {
        List<DonHang> listHD = donHangRepository.findAllHD(idKhachHang);
        List<DonHangResponse> responseList = new ArrayList<>();
        for (DonHang don : listHD) {
            DonHangResponse donHangResponse = new DonHangResponse();
            donHangResponse = DonHangMapping.mapEntitytoResponseBT(don);
            responseList.add(donHangResponse);
        }
        responseList.sort((o1, o2) -> o2.getNgayTao().compareTo(o1.getNgayTao()));
        return responseList;
    }

    @Override
    public List<DonHangResponse> findHDByStatus(Integer idKhachHang, Integer trangThaiDonHang) throws Exception {
        List<DonHang> listHD = donHangRepository.findHDByStatus(idKhachHang, trangThaiDonHang);
        List<DonHangResponse> responseList = new ArrayList<>();
        for (DonHang don : listHD) {
            DonHangResponse donHangResponse = new DonHangResponse();
            donHangResponse = DonHangMapping.mapEntitytoResponse(don);
            responseList.add(donHangResponse);
        }
        responseList.sort((o1, o2) -> o2.getNgayTao().compareTo(o1.getNgayTao()));
        return responseList;
    }

    @Override
    public DonHangResponse updateDH(DonHangRequest donHangRequest) {
        try {

            DonHang donHang = donHangRepository.findByIdDonHang(donHangRequest.getIdDonHang());
            if (donHang != null) {
                if (donHangRequest.getLyDo() == null) {
                    return null;
                } else {
                    donHang.setLyDo(donHangRequest.getLyDo());
                    donHang.setTrangThaiDonHang(donHangRequest.getTrangThaiDonHang());
                    donHang.setNgayCapNhap(new Date());
                    donHang = donHangRepository.save(donHang);
                }
            }

            DonHangResponse donHangResponse = DonHangMapping.mapEntitytoResponse(donHang);
            return donHangResponse;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DonHang> findByTrangThaiDonHang(int trangThai) {
        return donHangRepository.findByTrangThaiDonHang(trangThai);
    }

    @Override
    public Page<DonHang> getAllForBanHang(int pageNum) {
        Page<DonHang> allDonHang = donHangRepository.findAllSort(PageRequest.of(pageNum - 1, 5));
        return allDonHang;
    }

    @Override
    public DonHang findByMaDonHang(String maDonHang) {
        return donHangRepository.findByMaDonHang(maDonHang);
    }

    @Override
    public String thanhToanAdmin(DonHang donHang) {
        donHangRepository.updateTrangThaiDonHang(donHang);
        return "Don Hang Thanh Toan Thanh Cong" + donHang.getMaDonHang();
    }

    @Override
    public String xoaDonHangAdmin(DonHang donHang) {
        for (HoaDonChiTiet h : donHang.getListHoaDonChiTiet()) {
            maDinhDanhRepository.xoaSoLuongSanPham(h.getIdHoaDonChiTiet());
        }

        // xoa chi tiet hoa don
        hoaDonChiTietRepository.deleteByDonHang(donHang);
        // xoa hoa don
        donHangRepository.deleteByMaDonHang(donHang.getMaDonHang());
        return "Delete succcessful! Code is" + donHang.getMaDonHang();
    }

    @Override
    public void xoaDonHang(DonHang donhang) {
        donHangRepository.delete(donhang);
    }

    @Override
    public List<DonHang> getAllDonHang() {
        return donHangRepository.findAll();
    }

    @Override
    public List<DonHang> getAllPaginationDonHang() {
        return (List<DonHang>) donHangRepository.findAllSort((Pageable) Sort.by("maDonHang").ascending());
    }

    @Override
    public Page<DonHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, DONHANG_PAGE, Sort.by(SortOrder(sortField, sortDir)));
        if (keyword != null) {
            return donHangRepository.findAllPagination(keyword, pageable);
        }
        return donHangRepository.findAll(pageable);
    }

    public List<Sort.Order> SortOrder(String sort, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        if (sortDirection != null) {
            direction = Sort.Direction.fromString(sortDirection);
        } else {
            direction = Sort.Direction.DESC;
        }
        sorts.add(new Sort.Order(direction, sort));
        return sorts;
    }

    @Override
    public Page<DonHang> listByPageStatus(int pageNumber, String sortField, String sortDir, String keyword, int status) {
        Pageable pageable = PageRequest.of(pageNumber - 1, DONHANG_PAGE, Sort.by(SortOrder(sortField, sortDir)));
        if (keyword != null) {
            return donHangRepository.findAllPaginationStatus(keyword, pageable, status);
        }
        return donHangRepository.findAll(pageable);
    }

    @Override
    public Integer countDHbyStatus(Integer trangThaiDonhang) {
        return donHangRepository.countDHbyStatus(trangThaiDonhang);
    }

    @Override
    public Integer countDHAll() {
        return donHangRepository.countDHAll();
    }

    @Override
    public Integer soLuongImeiCoTheCapNhat(int idHDCT) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findByIdHoaDonChiTiet(idHDCT);
        return maDinhDanhRepository.soLuongDaMuaByHDCT(idHDCT) + maDinhDanhRepository.soLuongTonByHDCT(hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham());
    }
}

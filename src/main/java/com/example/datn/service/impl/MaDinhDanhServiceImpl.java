package com.example.datn.service.impl;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.entity.MaDinhDanhCTSP;
import com.example.datn.repository.HoaDonChiTietRepository;
import com.example.datn.repository.MaDinhDanhRepository;
import com.example.datn.service.MaDinhDanhService;
import com.example.datn.utils.TrangThaiMaDinhDanh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaDinhDanhServiceImpl implements MaDinhDanhService {

    @Autowired
    MaDinhDanhRepository repo;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public MaDinhDanhCTSP save(MaDinhDanhCTSP maDinhDanhCTSP) {
        return repo.save(maDinhDanhCTSP);
    }

    @Override
    public List<MaDinhDanhCTSP> saveMany(List<MaDinhDanhCTSP> maDinhDanhCTSP) {
        return repo.saveAll(maDinhDanhCTSP);
    }

    @Override
    public List<MaDinhDanhCTSP> getALl() {
        return repo.findAll();
    }

    @Override
    public MaDinhDanhCTSP get(Integer id) {
        if (repo.findById(id)!=null){
            return repo.findById(id).get();
        }
        return null;
    }

    @Override
    public Page<MaDinhDanhCTSP> searchMDD(int pageNumber, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        repo.findByIdMDDLike(keyword,pageable).getContent().forEach(item->{
        });
        return repo.findByIdMDDLike(keyword,pageable);
    }

    @Override
    public Integer countMaDinhDanh(Integer idChiTietSanPham) {
        return repo.countMaDinhDanh(idChiTietSanPham);
    }

    @Override
    public List<MaDinhDanhCTSP> findByChiTietSanPham(ChiTietSanPham chiTietSanPham, Integer soLuong) {
        Pageable pageable = PageRequest.of(0, soLuong);
        return repo.findByChiTietSanPhamAndTrangThai(chiTietSanPham, TrangThaiMaDinhDanh.CHUA_BAN,pageable);
    }

    @Override
    public void updateSoLuongAdmin(int idHDCT, int soLuongCapNhat) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findByIdHoaDonChiTiet(idHDCT);

        int soLuongHienTai = repo.soLuongDaMuaByHDCT(idHDCT);
        // nếu số lượng cập nhật > số lượng đã thêm
        if (soLuongCapNhat > soLuongHienTai) {
            repo.themSoLuongAdmin(hoaDonChiTiet.getIdHoaDonChiTiet(),repo.getListMaDinhDanh(soLuongCapNhat - soLuongHienTai,hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham()));
        }
        // nếu số lượng cập nhật < số lượng đã thêm
        if (soLuongCapNhat < soLuongHienTai) {
            repo.giamSoLuongAdmin(idHDCT,soLuongHienTai - soLuongCapNhat);
        }
        // update hdct
        hoaDonChiTiet.setSoLuong(soLuongCapNhat);
        hoaDonChiTietRepository.save(hoaDonChiTiet);
        // nếu số lượng cập nhật = số lượng đã thêm
    }

    @Override
    public List<MaDinhDanhCTSP> findByHoaDonChiTiet(Integer idhoaDonChiTiet) {
        return repo.findByHoaDonChiTiet(idhoaDonChiTiet);
    }
}

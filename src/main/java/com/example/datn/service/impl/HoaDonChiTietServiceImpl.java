package com.example.datn.service.impl;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.repository.ChiTietSanPhamRepository;
import com.example.datn.repository.HoaDonChiTietRepository;
import com.example.datn.repository.MaDinhDanhRepository;
import com.example.datn.request.HoaDonChiTietRequest;
import com.example.datn.service.ChiTietSanPhamService;
import com.example.datn.service.DonHangService;
import com.example.datn.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl  implements HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DonHangService donHangService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    MaDinhDanhRepository maDinhDanhRepository;
    @Override
    public HoaDonChiTiet save(HoaDonChiTiet hdct) {
        return hoaDonChiTietRepository.save(hdct);
    }

    @Override
    public List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang) {
        List<HoaDonChiTiet> result = new ArrayList<>();

        list.forEach(item -> {

            Integer chietKhau = null;
            ChiTietSanPham ctsp = chiTietSanPhamService.getChiTietSanPhamById(item.getIdChiTietSanPham());
            if(ctsp.getKhuyenMai() == null || ctsp.getKhuyenMai().isEnabled()== false){
                chietKhau = null;
            }else{
                chietKhau = ctsp.getKhuyenMai().getChietKhau();
            }
            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .donHang(donHangService.getById(idDonHang))
                    .chiTietSanPham(ctsp)
                    .soLuong(item.getSoLuong())
                    .chietKhau(chietKhau)
                    .build();

            if(chietKhau== null){
                hdct.setGiaBan(ctsp.getGiaSanPham());
            }else{
                hdct.setGiaBan(ctsp.getGiaSanPham() - ctsp.getGiaSanPham()*chietKhau/100);
            }
            result.add(hdct);
        });
        return result;
    }

    @Override
    public List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT) {
        hoaDonChiTietRepository.saveAll(listHDCT);
        return null;
    }

    @Override
    public Double getTongGia(List<HoaDonChiTietRequest> list) {
        Double result = 0D;
        for (int i = 0; i < list.size(); i++) {
            Double giaBan = 0.0;
            ChiTietSanPham ctsp = chiTietSanPhamRepository.findByIdChiTietSanPham(list.get(i).getIdChiTietSanPham());
            if( ctsp.getKhuyenMai()!= null && ctsp.getKhuyenMai().isEnabled()){
                giaBan = ctsp.getGiaSanPham()-ctsp.getGiaSanPham()*ctsp.getKhuyenMai().getChietKhau()/100;
            }else{
                giaBan = ctsp.getGiaSanPham();
            }
            result += list.get(i).getSoLuong() * giaBan;
        }
        return result;
    }

    @Override
    public List<HoaDonChiTiet> getByIdDonHang(int id) {
        return hoaDonChiTietRepository.findHDCTBYIdDonHang(id);
    }

    @Override
    public List<HoaDonChiTiet> getByHoaDonId(DonHang donHang) {
        return hoaDonChiTietRepository.findByDonHang(donHang);
    }

    @Override
    public List<HoaDonChiTiet> getHDCTByMaDonHang(String maDonHang) {
        List<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepository.findByMaDonHang(maDonHang);
        return hoaDonChiTietPage;
    }

    @Override
    public void themSoLuongSanPham(int soLuong, ChiTietSanPham chiTietSanPham, DonHang donHang) {
        int existIdHCT = -1;
        for (HoaDonChiTiet hoaDonChiTiet : donHang.getListHoaDonChiTiet()){
            if (hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham() == chiTietSanPham.getIdChiTietSanPham()){
                existIdHCT = hoaDonChiTiet.getIdHoaDonChiTiet();
                break;
            }
        }

        if (existIdHCT==-1){
            HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.save(HoaDonChiTiet
                    .builder()
                    .chiTietSanPham(chiTietSanPham)
                    .donHang(donHang)
                    .soLuong(soLuong)
                    .chietKhau((chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false) ? null : chiTietSanPham.getKhuyenMai().getChietKhau())
                    .build());

            if(chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false){
                hoaDonChiTiet.setGiaBan(chiTietSanPham.getGiaSanPham());
            }else{
                hoaDonChiTiet.setGiaBan(chiTietSanPham.getGiaSanPham() - chiTietSanPham.getGiaSanPham()* hoaDonChiTiet.getChietKhau()/100);
            }
            List<Integer> listMaDinhDanh = maDinhDanhRepository.getListMaDinhDanh(soLuong,chiTietSanPham.getIdChiTietSanPham());
            maDinhDanhRepository.themSoLuongAdmin(hoaDonChiTiet.getIdHoaDonChiTiet(),listMaDinhDanh);
        } else{
            hoaDonChiTietRepository.updateSoLuongSanPham(soLuong,existIdHCT);
            List<Integer> listMaDinhDanh = maDinhDanhRepository.getListMaDinhDanh(soLuong,chiTietSanPham.getIdChiTietSanPham());
            maDinhDanhRepository.themSoLuongAdmin(existIdHCT,listMaDinhDanh);
        }
    }

    @Transactional
    @Override
    public void xoaHDCT(HoaDonChiTiet hoaDonChiTiet) {
        maDinhDanhRepository.xoaSoLuongSanPham(hoaDonChiTiet.getIdHoaDonChiTiet());
        hoaDonChiTietRepository.xoaHDCT(hoaDonChiTiet.getIdHoaDonChiTiet());
    }

    @Override
    public HoaDonChiTiet findHoaDonChiTietById(int id) {
        return hoaDonChiTietRepository.findByIdHoaDonChiTiet(id);
    }

    @Override
    public void xoaHDCTByIdDonHang(DonHang donHang) {
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.findByDonHang(donHang);
        hoaDonChiTietRepository.deleteAll(listHDCT);
    }
}

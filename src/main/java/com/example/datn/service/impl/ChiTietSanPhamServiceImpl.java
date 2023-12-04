package com.example.datn.service.impl;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.VeAo;
import com.example.datn.exception.ChiTietSanPhamNotFountException;
import com.example.datn.repository.ChiTietSanPhamRepository;
import com.example.datn.repository.MaDinhDanhRepository;
import com.example.datn.response.SanPhamAdminResponse;
import com.example.datn.response.TimKiemSettingResponse;
import com.example.datn.service.CauTrucKhuyService;
import com.example.datn.service.ChatLieuService;
import com.example.datn.service.ChiTietSanPhamService;
import com.example.datn.service.DanhmucService;
import com.example.datn.service.HoaTietService;
import com.example.datn.service.KhuyenMaiService;
import com.example.datn.service.KichCoService;
import com.example.datn.service.KieuDetService;
import com.example.datn.service.KieuTuiService;
import com.example.datn.service.LopLotService;
import com.example.datn.service.MauSacService;
import com.example.datn.service.VeAoService;
import com.example.datn.service.XeTaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service

public class ChiTietSanPhamServiceImpl  implements ChiTietSanPhamService {
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DanhmucService danhmucService;
    @Autowired
    KhuyenMaiService khuyenMaiService;
    @Autowired
    MauSacService mauSacService;
    @Autowired
    KichCoService kichCoService;
    @Autowired
    ChatLieuService chatLieuService;
    @Autowired
    CauTrucKhuyService cauTrucKhuyService;
    @Autowired
    HoaTietService hoaTietService;
    @Autowired
    KieuTuiService kieuTuiService;
    @Autowired
    KieuDetService kieuDetService;
    @Autowired
    LopLotService lopLotService;
    @Autowired
    XeTaService xeTaService;
    @Autowired
    VeAoService veAoService;
    @Autowired
    MaDinhDanhRepository maDinhDanhRepository;
    @Override
    public int totalPageSearchSP(String key, int pageNum) {
        return chiTietSanPhamRepository.searchByKey(key, PageRequest.of(pageNum - 1, 5)).getTotalPages();
    }

    @Override
    public List<SanPhamAdminResponse> searchSP(String key, int pageNum) {
        List<ChiTietSanPham> lstChiTietSanPhams = chiTietSanPhamRepository.searchByKey(key,PageRequest.of(pageNum - 1, 5)).getContent();

        List<SanPhamAdminResponse> lst = lstChiTietSanPhams
                .stream()
                .map(lstSP -> SanPhamAdminResponse
                        .builder()
                        .idChiTietSanPham(lstSP.getIdChiTietSanPham())
                        .maChiTietSanPham(lstSP.getMaChiTietSanPham())
                        .giaSanPham(lstSP.getGiaSanPham())
                        .sanPham(lstSP.getSanPham())
                        .mauSac(lstSP.getMauSac())
                        .khuyenMai(lstSP.getKhuyenMai())
                        .soLuong(maDinhDanhRepository.countByIdCTSPEnabled(lstSP.getIdChiTietSanPham())) // find All  status is enabled
                        .chatLieu(lstSP.getChatLieu())
                        .cauTrucKhuy(lstSP.getCauTrucKhuy())
                        .hoaTiet(lstSP.getHoaTiet())
                        .kichCo(lstSP.getKichCo())
                        .kieuDet(lstSP.getKieuDet())
                        .kieuTui(lstSP.getKieuTui())
                        .lopLot(lstSP.getLopLot())
                        .veAo(lstSP.getVeAo())
                        .xeTa(lstSP.getXeTa())
                        .build()).collect(Collectors.toList());
        return lst;
    }

    @Override
    public TimKiemSettingResponse getTimKiemSetting() {
        TimKiemSettingResponse result = new TimKiemSettingResponse();
        result.setListDanhMuc(danhmucService.listAll());
        result.setListKichCo(kichCoService.getAllKichCo());
        result.setListMauSac(mauSacService.getAllMauSac());
        result.setListChatLieu(chatLieuService.getAllChatLieu());
        result.setListCauTrucKhuy(cauTrucKhuyService.getAllCauTrucKhuy());
        result.setListHoaTiet(hoaTietService.getAllHoaTiet());
        result.setListKieuDet(kieuDetService.getAllKieuDet());
        result.setListKieuTui(kieuTuiService.getAllKieuTui());
        result.setListLopLot(lopLotService.getAllLopLot());
        result.setListVeAo(veAoService.getAllVeAo());
        result.setListXeTa(xeTaService.getAllXeTa());
        return result;
    }

    @Override
    public ChiTietSanPham getChiTietSanPhamById(Integer id) {
        return chiTietSanPhamRepository.findById(id).get();
    }

    @Override
    public ChiTietSanPham update(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public Page<ChiTietSanPham> findByMaSP(String maSanPham, int pageNum) {
        return chiTietSanPhamRepository.findByMaSP(maSanPham,PageRequest.of(pageNum - 1, 5));
    }

    @Override
    public Page<ChiTietSanPham> getALlChiTietSanPhamPage(int pageNum) {
        return chiTietSanPhamRepository.findAllHung(PageRequest.of(pageNum - 1, 5));
    }

    @Override
    public List<SanPhamAdminResponse> getAllSanPhamAminResponse(int pageNum) {
        List<ChiTietSanPham> lstChiTietSanPhams = getALlChiTietSanPhamPage(pageNum).getContent();

        List<SanPhamAdminResponse> lst = lstChiTietSanPhams
                .stream()
                .map(lstSP -> SanPhamAdminResponse
                        .builder()
                        .idChiTietSanPham(lstSP.getIdChiTietSanPham())
                        .maChiTietSanPham(lstSP.getMaChiTietSanPham())
                        .giaSanPham(lstSP.getGiaSanPham())
                        .sanPham(lstSP.getSanPham())
                        .mauSac(lstSP.getMauSac())
                        .khuyenMai(lstSP.getKhuyenMai())
                        .soLuong(maDinhDanhRepository.countByIdCTSPEnabled(lstSP.getIdChiTietSanPham()))  // find All  status is enabled
                        .chatLieu(lstSP.getChatLieu())
                        .cauTrucKhuy(lstSP.getCauTrucKhuy())
                        .hoaTiet(lstSP.getHoaTiet())
                        .kichCo(lstSP.getKichCo())
                        .kieuDet(lstSP.getKieuDet())
                        .kieuTui(lstSP.getKieuTui())
                        .lopLot(lstSP.getLopLot())
                        .veAo(lstSP.getVeAo())
                        .xeTa(lstSP.getXeTa())
                        .build()).collect(Collectors.toList());
        return lst;
    }

    @Override
    public ChiTietSanPham findByMaChiTietSanPham(String maChimaTietSanPham) {
        return chiTietSanPhamRepository.findByMaChiTietSanPham(maChimaTietSanPham);
    }

    @Override
    public ChiTietSanPham getChiTietSanPhamByMa(String ma) {
        return chiTietSanPhamRepository.findByMaChiTietSanPham(ma);
    }

    @Override
    public List<ChiTietSanPham> listAll() {
        return chiTietSanPhamRepository.findAll(Sort.by("maChiTietSanPham").ascending());
    }

    @Override
    public boolean isUniqueChiTietSanPham(String maChiTietSanPham, String tenSanPham, String tenMauSac, String tenChatLieu, String tenKichCo, String tenCauTrucKhuy, String tenHoaTiet, String tenKieuTui, String tenKieuDet, String tenLopLot, String tenXeTa, String tenVeAo) {
        ChiTietSanPham existingByMa = chiTietSanPhamRepository.findByMaChiTietSanPham(maChiTietSanPham);
        ChiTietSanPham existingByNames = chiTietSanPhamRepository.findBySanPham_TenSanPhamAndMauSac_TenMauSacAndChatLieu_TenChatLieuAndKichCo_TenKichCoAndCauTrucKhuy_TenCauTrucKhuyAndHoaTiet_TenHoaTietAndKieuTui_TenKieuTuiAndKieuDet_TenKieuDetAndLopLot_TenLopLotAndVeAo_TenVeAoAndXeTa_TenXeTa(
                tenSanPham,tenMauSac,tenChatLieu,tenKichCo,tenCauTrucKhuy,tenHoaTiet,tenKieuTui,tenKieuDet,tenLopLot,tenVeAo,tenXeTa
        );

        return (existingByMa == null && existingByNames == null);
    }

    @Override
    public boolean isUniqueChiTietSanPhamUpdate(Integer idChiTietSanPham, String maChiTietSanPham, String tenSanPham, String tenMauSac, String tenChatLieu, String tenKichCo, String tenCauTrucKhuy, String tenHoaTiet, String tenKieuTui, String tenKieuDet, String tenLopLot, String tenVeAo, String tenXeTa) {
        ChiTietSanPham existingById = chiTietSanPhamRepository.findByIdChiTietSanPham(idChiTietSanPham);
        ChiTietSanPham existingByMa = chiTietSanPhamRepository.findByMaChiTietSanPham(maChiTietSanPham);
        ChiTietSanPham existingByNames = chiTietSanPhamRepository.findBySanPham_TenSanPhamAndMauSac_TenMauSacAndChatLieu_TenChatLieuAndKichCo_TenKichCoAndCauTrucKhuy_TenCauTrucKhuyAndHoaTiet_TenHoaTietAndKieuTui_TenKieuTuiAndKieuDet_TenKieuDetAndLopLot_TenLopLotAndVeAo_TenVeAoAndXeTa_TenXeTa(
                tenSanPham,tenMauSac,tenChatLieu,tenKichCo,tenCauTrucKhuy,tenHoaTiet,tenKieuTui,tenKieuDet,tenLopLot,tenVeAo,tenXeTa
        );

        return (existingByMa == null && existingByNames == null && existingById == null);
    }

    @Override
    public ChiTietSanPham save(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham get(Integer id) throws ChiTietSanPhamNotFountException {
        try{
            return chiTietSanPhamRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new ChiTietSanPhamNotFountException("không tìm thấy sản phẩm có id" + id);
        }
    }

    @Override
    public Page<ChiTietSanPham> listByPageAndProductName(int pageNum, String sortField, String sortDir, String keyword, String productName) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_DETAIL_PER_PAGE, sort);

        if (StringUtils.isEmpty(productName) && StringUtils.isEmpty(keyword)) {
            return chiTietSanPhamRepository.findAll(pageable);
        } else if (StringUtils.isEmpty(productName)) {
            return chiTietSanPhamRepository.findByKeyword(keyword, pageable);
        } else if (StringUtils.isEmpty(keyword)) {
            return chiTietSanPhamRepository.findBySanPham_TenSanPhamContainingIgnoreCase(productName, pageable);
        } else {
            return chiTietSanPhamRepository.findByProductNameAndKeyword(keyword, productName, pageable);
        }
    }

    @Override
    public List<ChiTietSanPham> findByIdSp(Integer IdSP) {
        return chiTietSanPhamRepository.findByIdSp(IdSP);
    }
}

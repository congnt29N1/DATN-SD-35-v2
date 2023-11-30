package com.example.datn.service.impl;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.SanPham;
import com.example.datn.exception.SanPhamNotFountException;
import com.example.datn.mapper.ChiTietSanPhamMapping;
import com.example.datn.mapper.SanPhamMapping;
import com.example.datn.repository.SanPhamRepository;
import com.example.datn.request.TimKiemRequest;
import com.example.datn.response.ChiTietSanPhamResponse;
import com.example.datn.response.SanPhamDetailResponse;
import com.example.datn.response.TimKiemResponse;
import com.example.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest) {
        HashMap<String,Object> values = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT sp FROM SanPham sp ");
        query.append("INNER JOIN DanhMuc dm ON sp.danhMuc.id = dm.id ");
        query.append("INNER JOIN ChiTietSanPham ctsp ON sp.idSanPham = ctsp.sanPham.idSanPham ");
        query.append("INNER JOIN KichCo kc ON ctsp.kichCo.idKichCo = kc.idKichCo ");
        query.append("INNER JOIN MauSac ms ON ctsp.mauSac.idMauSac = ms.idMauSac ");
        query.append("INNER JOIN ChatLieu cl ON ctsp.chatLieu.idChatLieu = cl.idChatLieu ");
        query.append("INNER JOIN CauTrucKhuy ctk ON ctsp.cauTrucKhuy.idCauTrucKhuy = ctk.idCauTrucKhuy ");
        query.append("INNER JOIN HoaTiet ht ON ctsp.hoaTiet.idHoaTiet = ht.idHoaTiet ");
        query.append("INNER JOIN KieuTui kt ON ctsp.kieuTui.idKieuTui = kt.idKieuTui ");
        query.append("INNER JOIN KieuDet kd ON ctsp.kieuDet.idKieuDet = kd.idKieuDet ");
        query.append("INNER JOIN LopLot ll ON ctsp.lopLot.idLopLot = ll.idLopLot ");
        query.append("INNER JOIN VeAo va ON ctsp.veAo.idVeAo = va.idVeAo ");
        query.append("INNER JOIN XeTa xt ON ctsp.xeTa.idXeTa = xt.idXeTa ");
        query.append("WHERE true = true ");
        if (timKiemRequest.getDanhMucId() != null && timKiemRequest.getDanhMucId().size() != 0) {
            query.append("AND sp.danhMuc.id IN :dms ");
            values.put("dms",timKiemRequest.getDanhMucId());
        }
        if (timKiemRequest.getKichCoId() != null && timKiemRequest.getKichCoId().size() != 0) {
            query.append("AND ctsp.kichCo.idKichCo IN :kcs ");
            values.put("kcs",timKiemRequest.getKichCoId());
        }
        if (timKiemRequest.getMauSacId() != null && timKiemRequest.getMauSacId().size() != 0) {
            query.append("AND ctsp.mauSac.idMauSac IN :mss ");
            values.put("mss",timKiemRequest.getMauSacId());
        }
        if (timKiemRequest.getChatLieuId() != null && timKiemRequest.getChatLieuId().size() != 0) {
            query.append("AND ctsp.chatLieu.idChatLieu IN :cls ");
            values.put("cls",timKiemRequest.getChatLieuId());
        }
        if (timKiemRequest.getCauTrucKhuyId() != null && timKiemRequest.getCauTrucKhuyId().size() != 0) {
            query.append("AND ctsp.cauTrucKhuy.idCauTrucKhuy IN :ctks ");
            values.put("ctks",timKiemRequest.getCauTrucKhuyId());
        }
        if (timKiemRequest.getHoaTietId() != null && timKiemRequest.getHoaTietId().size() != 0) {
            query.append("AND ctsp.hoaTiet.idHoaTiet IN :hts ");
            values.put("hts",timKiemRequest.getHoaTietId());
        }
        if (timKiemRequest.getKieuTuiId() != null && timKiemRequest.getKieuTuiId().size() != 0) {
            query.append("AND ctsp.kieuTui.idKieuTui IN :kts ");
            values.put("kts",timKiemRequest.getKieuTuiId());
        }
        if (timKiemRequest.getKieuDetId() != null && timKiemRequest.getKieuDetId().size() != 0) {
            query.append("AND ctsp.kieuDet.idKieuDet IN :kds ");
            values.put("kds",timKiemRequest.getKieuDetId());
        }
        if (timKiemRequest.getLopLotId() != null && timKiemRequest.getLopLotId().size() != 0) {
            query.append("AND ctsp.lopLot.idLopLot IN :lls ");
            values.put("lls",timKiemRequest.getLopLotId());
        }
        if (timKiemRequest.getVeAoId() != null && timKiemRequest.getVeAoId().size() != 0) {
            query.append("AND ctsp.veAo.idVeAo IN :vas ");
            values.put("vas",timKiemRequest.getVeAoId());
        }
        if (timKiemRequest.getXeTaId() != null && timKiemRequest.getXeTaId().size() != 0) {
            query.append("AND ctsp.xeTa.idXeTa IN :xts ");
            values.put("xts",timKiemRequest.getXeTaId());
        }
        if (timKiemRequest.getTenSanPham() != null && !timKiemRequest.getTenSanPham().trim().isBlank()) {
            query.append("AND sp.tenSanPham like :ten ");
            values.put("ten", "%"+timKiemRequest.getTenSanPham()+"%");
        }
        Query queryCreated = entityManager.createQuery(query.toString(), SanPham.class);
        values.forEach((key,value)->{
            queryCreated.setParameter(key,value);
        });
        System.out.println(queryCreated.toString());
        List<SanPham> listSanPham = queryCreated.getResultList();
        Set<TimKiemResponse> result = new HashSet<>();
        listSanPham.forEach(sanPham -> result.add(toTimKiemResponse(sanPham)));
        return result;
    }

    private TimKiemResponse toTimKiemResponse(SanPham sp) {
        TimKiemResponse result = new TimKiemResponse();
        result.setSanPhamID(sp.getIdSanPham());
        result.setTenSanPham(sp.getTenSanPham());
//        result.setGiaSanPham(sp.getGiaSanPham());
        result.setListAnhSanPham(sp.getListAnhSanPham());
        result.setListChiTietSanPham(sp.getListChiTietSanPham());
        return result;
    }

    @Override
    public List<SanPhamDetailResponse> getSPnew() {
        List<SanPham> listSanPham = sanPhamRepository.getSPnew();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<SanPhamDetailResponse> getSPFeature() {
        List<SanPham> listSanPham = sanPhamRepository.findAll();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<SanPhamDetailResponse> getSPchay() {
        List<SanPham> listSanPham = sanPhamRepository.getSPchay();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId).get();
        return toSanPhamRepository(sanPham);
    }

    private SanPhamDetailResponse toSanPhamRepository(SanPham sp) {
        return SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .listAnhSanPham(sp.getListAnhSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .tenSanPham(sp.getTenSanPham())
                .maSanPham(sp.getMaSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .trangThai(sp.getTrangThai())
                .build();
    }

    @Override
    public List<ChiTietSanPhamResponse> getSPchayKM(Integer idChiTietSanPham) {
        List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();
        List<ChiTietSanPham> chiTietSanPhams = sanPhamRepository.getCTSP(idChiTietSanPham);
        Date currentDate = new Date();
        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            if (chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false
                    || chiTietSanPham.getKhuyenMai().getNgayKetThuc().before(currentDate)
                    || chiTietSanPham.getKhuyenMai().getNgayBatDau().after(currentDate)) {
                chiTietSanPhamList.add(chiTietSanPham);
            } else {
                chiTietSanPham.setGiaSanPham(chiTietSanPham.getGiaSanPham() - chiTietSanPham.getGiaSanPham() * chiTietSanPham.getKhuyenMai().getChietKhau() / 100);
                chiTietSanPhamList.add(chiTietSanPham);
            }
        }
        return chiTietSanPhamList.stream().map(ChiTietSanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());

    }

    @Override
    public List<SanPham> listAll() {
        return sanPhamRepository.findAll(Sort.by("tenSanPham").ascending());
    }

    @Override
    public SanPham save(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public SanPham get(Integer id) throws SanPhamNotFountException {
        try{
            return sanPhamRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new SanPhamNotFountException("không tìm thấy sản phẩm có id" + id);
        }
    }

    @Override
    public Page<SanPham> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 ,PRODUCT_PER_PAGE, sort);
        if (keyword != null){
            return sanPhamRepository.findAll(keyword,pageable);
        }
        return sanPhamRepository.findAll(pageable);

    }

    @Override
    public boolean checkUniqueTenAndMa(String ten, String ma) {
        SanPham sanPhamTheoTen = sanPhamRepository.findByTenSanPham(ten);
        SanPham sanPhamTheoMa = sanPhamRepository.findByMaSanPham(ma);

        if (sanPhamTheoTen == null && sanPhamTheoMa == null) {
            return true; // Tên và mã đều không bị trùng
        }

        return false; // Tên hoặc mã đã bị trùng
    }

    @Override
    public boolean checkUniqueTenMaId(String ten, String ma, Integer id) {
        SanPham sanPhamTheoTen = sanPhamRepository.findByTenSanPham(ten);
        SanPham sanPhamTheoMa = sanPhamRepository.findByMaSanPham(ma);

        if (sanPhamTheoTen == null && sanPhamTheoMa == null) {
            return true; // Tên và mã đều không bị trùng
        }

        if (id != null) {
            SanPham existingSanPham = sanPhamRepository.findById(id).orElse(null);
            if (existingSanPham != null) {
                if (sanPhamTheoTen != null && !sanPhamTheoTen.getIdSanPham().equals(id)) {
                    return false; // Tên trùng nhưng không phải sản phẩm hiện tại
                }
                if (sanPhamTheoMa != null && !sanPhamTheoMa.getIdSanPham().equals(id)) {
                    return false; // Mã trùng nhưng không phải sản phẩm hiện tại
                }
            }
        }

        return true; // Trường hợp còn lại
    }
}

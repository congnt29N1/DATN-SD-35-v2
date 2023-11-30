package com.example.datn.service.impl;

;
import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.exception.CauTrucKhuyNotFoundException;
import com.example.datn.repository.CauTrucKhuyRepository;
import com.example.datn.service.CauTrucKhuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CauTrucKhuyServiceImpl implements CauTrucKhuyService {
    @Autowired
    CauTrucKhuyRepository cauTrucKhuyRepository;

    @Override
    public List<CauTrucKhuy> getAllCauTrucKhuy() {
        return cauTrucKhuyRepository.findAll();
    }

    @Override
    public List<CauTrucKhuy> getAllPaginationCauTrucKhuy() {
        return cauTrucKhuyRepository.findAll(Sort.by("tenCauTrucKhuy").ascending());
    }

    @Override
    public Page<CauTrucKhuy> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return cauTrucKhuyRepository.findAll(keyword,pageable);
        }
        return cauTrucKhuyRepository.findAll(pageable);
    }

    @Override
    public CauTrucKhuy save(CauTrucKhuy cauTrucKhuy) {
        return cauTrucKhuyRepository.save(cauTrucKhuy);
    }

    @Override
    public CauTrucKhuy get(Integer id) throws CauTrucKhuyNotFoundException, Exception {
        try {
            return cauTrucKhuyRepository.findById(id)
                    .orElseThrow(() -> new CauTrucKhuyNotFoundException("Không tìm thấy vật liệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        CauTrucKhuy cauTrucKhuyTheoTen = cauTrucKhuyRepository.findByTenCauTrucKhuy(ten);
        if (cauTrucKhuyTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (cauTrucKhuyTheoTen != null) {
                return false;
            }
        }else {
            if (cauTrucKhuyTheoTen.getIdCauTrucKhuy() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateCauTrucKhuyEnabledStatus(Integer id, boolean enabled) {
cauTrucKhuyRepository.updateEnabledStatus(id,enabled);
    }
}

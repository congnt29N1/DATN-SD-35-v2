package com.example.datn.service.impl;

import com.example.datn.entity.KieuDet;
import com.example.datn.exception.KieuDetNotFoundException;
import com.example.datn.repository.KieuDetRepository;
import com.example.datn.service.KieuDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuDetServiceImpl implements KieuDetService {
    @Autowired
    KieuDetRepository kieuDetRepository;

    @Override
    public List<KieuDet> getAllKieuDet() {
        return kieuDetRepository.findAll();
    }

    @Override
    public List<KieuDet> getAllPaginationKieuDet() {
        return kieuDetRepository.findAll(Sort.by("tenKieuDet").ascending());
    }

    @Override
    public Page<KieuDet> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return kieuDetRepository.findAll(keyword,pageable);
        }
        return kieuDetRepository.findAll(pageable);
    }

    @Override
    public KieuDet save(KieuDet kieuDet) {
        return kieuDetRepository.save(kieuDet);
    }

    @Override
    public KieuDet get(Integer id) throws KieuDetNotFoundException, Exception {
        try {
            return kieuDetRepository.findById(id)
                    .orElseThrow(() -> new KieuDetNotFoundException("Không tìm thấy vật liệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        KieuDet kieuDetTheoTen = kieuDetRepository.findByTenKieuDet(ten);
        if (kieuDetTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (kieuDetTheoTen != null) {
                return false;
            }
        }else {
            if (kieuDetTheoTen.getIdKieuDet() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateKieuDetEnabledStatus(Integer id, boolean enabled) {
            kieuDetRepository.updateEnabledStatus(id,enabled);
    }
}

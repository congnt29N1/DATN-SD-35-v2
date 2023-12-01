package com.example.datn.service.impl;

import com.example.datn.entity.KieuTui;
import com.example.datn.exception.kieuTuiNotFoundException;
import com.example.datn.repository.KieuTuiRepository;
import com.example.datn.service.KieuTuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuTuiServiceImpl implements KieuTuiService {
    @Autowired
    KieuTuiRepository kieuTuiRepository;

    @Override
    public List<KieuTui> getAllKieuTui() {
        return kieuTuiRepository.findAll();
    }

    @Override
    public List<KieuTui> getAllPaginationKieuTui() {
        return kieuTuiRepository.findAll(Sort.by("tenKieuTui").ascending());
    }

    @Override
    public Page<KieuTui> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return kieuTuiRepository.findAll(keyword,pageable);
        }
        return kieuTuiRepository.findAll(pageable);
    }

    @Override
    public KieuTui save(KieuTui kieuTui) {
        return kieuTuiRepository.save(kieuTui);
    }

    @Override
    public KieuTui get(Integer id) throws kieuTuiNotFoundException, Exception {
        try {
            return kieuTuiRepository.findById(id)
                    .orElseThrow(() -> new kieuTuiNotFoundException("Không tìm thấy vật liệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        KieuTui kieuTuiTheoTen = kieuTuiRepository.findByTenKieuTui(ten);
        if (kieuTuiTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (kieuTuiTheoTen != null) {
                return false;
            }
        }else {
            if (kieuTuiTheoTen.getIdKieuTui() != id) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void updateKieuTuiEnabledStatus(Integer id, boolean enabled) {
        kieuTuiRepository.updateEnabledStatus(id,enabled);
    }
}

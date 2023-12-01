package com.example.datn.service.impl;


import com.example.datn.entity.LopLot;
import com.example.datn.exception.LopLotNotFoundException;
import com.example.datn.repository.LopLotRepository;
import com.example.datn.service.LopLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopLotServiceImpl implements LopLotService {
    @Autowired
    LopLotRepository lopLotRepository;

    @Override
    public List<LopLot> getAllLopLot() {
        return lopLotRepository.findAll();
    }

    @Override
    public List<LopLot> getAllPaginationLopLot() {
        return lopLotRepository.findAll(Sort.by("tenLopLot").ascending());
    }

    @Override
    public Page<LopLot> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return lopLotRepository.findAll(keyword,pageable);
        }
        return lopLotRepository.findAll(pageable);
    }

    @Override
    public LopLot save(LopLot lopLot) {
        return lopLotRepository.save(lopLot);
    }

    @Override
    public LopLot get(Integer id) throws LopLotNotFoundException, Exception {
        try {
            return lopLotRepository.findById(id)
                    .orElseThrow(() -> new LayerInstantiationException("Không tìm thấy Lớp lót nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        LopLot lopLotTheoTen = lopLotRepository.findByTenLopLot(ten);
        if (lopLotTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (lopLotTheoTen != null) {
                return false;
            }
        }else {
            if (lopLotTheoTen.getIdLopLot() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateLopLotEnabledStatus(Integer id, boolean enabled) {

    }
}

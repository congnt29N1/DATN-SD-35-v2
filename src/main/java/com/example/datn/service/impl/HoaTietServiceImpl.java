package com.example.datn.service.impl;


import com.example.datn.entity.HoaTiet;
import com.example.datn.exception.HoaTietNotFoundException;
import com.example.datn.repository.HoaTietRespository;
import com.example.datn.service.HoaTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaTietServiceImpl implements HoaTietService {
    public static final int CATEGORIES_PER_PAGE = 4;
    @Autowired
    HoaTietRespository hoaTietRespository;


    @Override
    public List<HoaTiet> getAllHoaTiet() {
        return hoaTietRespository.findAll();
    }

    @Override
    public List<HoaTiet> getAllPaginationHoaTiet() {
        return hoaTietRespository.findAll(Sort.by("tenHoaTiet").ascending());
    }

    @Override
    public Page<HoaTiet> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, MATERIALS_PER_PAGE, sort);
        if (keyword != null) {
            return hoaTietRespository.findAll(keyword, pageable);
        }
        return hoaTietRespository.findAll(pageable);
    }

    @Override
    public HoaTiet save(HoaTiet hoaTiet) {
        return hoaTietRespository.save(hoaTiet);
    }

    @Override
    public HoaTiet get(Integer id) throws HoaTietNotFoundException, Exception {
        try {
            return hoaTietRespository.findById(id)
                    .orElseThrow(() -> new HoaTietNotFoundException("Không tìm thấy họa tiết nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        HoaTiet hoaTietTheoTen = hoaTietRespository.findByTenHoaTiet(ten);
        if (hoaTietTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (hoaTietTheoTen != null) {
                return false;
            }
        }else {
            if (hoaTietTheoTen.getIdHoaTiet() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateHoaTietEnabledStatus(Integer id, boolean enabled) {
    hoaTietRespository.updateEnabledStatus(id, enabled);
    }
}

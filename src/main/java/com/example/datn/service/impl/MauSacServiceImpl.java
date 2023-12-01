package com.example.datn.service.impl;

import com.example.datn.entity.MauSac;
import com.example.datn.exception.MauSacNotFoundException;
import com.example.datn.repository.MauSacRepository;
import com.example.datn.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacServiceImpl  implements MauSacService {
    public static final int COLORS_PER_PAGE = 2;
    @Autowired
    MauSacRepository mauSacRepository;
    @Override
    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    @Override
    public Page<MauSac> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, COLORS_PER_PAGE, sort);

        if(keyword != null){
            return mauSacRepository.findAll(keyword,pageable);
        }

        return mauSacRepository.findAll(pageable);
    }

    @Override
    public void updateMauSacEnabledStatus(Integer id, boolean enabled) {
        mauSacRepository.updateEnabledStatus(id,enabled) ;
    }

    @Override
    public MauSac save(MauSac mauSac) {
        return mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac get(Integer id) throws Exception {
        try {
            return mauSacRepository.findById(id)
                    .orElseThrow(() -> new MauSacNotFoundException("Không tìm thấy danh mục nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}

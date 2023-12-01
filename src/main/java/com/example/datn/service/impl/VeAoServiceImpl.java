package com.example.datn.service.impl;


import com.example.datn.entity.VeAo;
import com.example.datn.exception.VeAoNotFoundException;
import com.example.datn.repository.VeAoRepository;
import com.example.datn.service.VeAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeAoServiceImpl implements VeAoService {
    @Autowired
    VeAoRepository veAoRepository;

    @Override
    public List<VeAo> getAllVeAo() {
        return veAoRepository.findAll();
    }

    @Override
    public List<VeAo> getAllPaginationVeAo() {
        return veAoRepository.findAll(Sort.by("tenVeAo").ascending());
    }

    @Override
    public Page<VeAo> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return veAoRepository.findAll(keyword,pageable);
        }
        return veAoRepository.findAll(pageable);
    }

    @Override
    public VeAo save(VeAo veao) {
        return veAoRepository.save(veao);
    }

    @Override
    public VeAo get(Integer id) throws VeAoNotFoundException, Exception {
        try {
            return veAoRepository.findById(id)
                    .orElseThrow(() -> new VeAoNotFoundException("Không tìm thấy ve áo nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        VeAo veAoTheoTen = veAoRepository.findByTenVeAo(ten);
        if (veAoTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (veAoTheoTen != null) {
                return false;
            }
        }else {
            if (veAoTheoTen.getIdVeAo() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateVeAoEnabledStatus(Integer id, boolean enabled) {
veAoRepository.updateEnabledStatus(id,enabled);
    }
}

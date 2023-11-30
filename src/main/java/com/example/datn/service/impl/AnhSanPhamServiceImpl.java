package com.example.datn.service.impl;

import com.example.datn.entity.AnhSanPham;
import com.example.datn.repository.AnhSanPhamRepository;
import com.example.datn.service.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnhSanPhamServiceImpl implements AnhSanPhamService {
    @Autowired
    AnhSanPhamRepository anhSanPhamRepository;
    @Override
    public AnhSanPham save(AnhSanPham anhSanPham) {
        return anhSanPhamRepository.save(anhSanPham);
    }
}

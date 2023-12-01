package com.example.datn.service;

import com.example.datn.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MauSacService {
    List<MauSac> getAllMauSac();

    Page<MauSac> listByPage(int pageNum, String sortField, String sortDir, String keyword);

    void updateMauSacEnabledStatus(Integer id, boolean enabled);

    MauSac save(MauSac mauSac);

    MauSac get(Integer id) throws Exception;
}

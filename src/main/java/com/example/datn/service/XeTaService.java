package com.example.datn.service;


import com.example.datn.entity.XeTa;
import org.springframework.data.domain.Page;

import java.util.List;

public interface XeTaService {
    List<XeTa> getAllXeTa();

    Page<XeTa> listByPage(int pageNum, String sortField, String sortDir, String keyword);

    void updateXeTaEnabledStatus(Integer id, boolean enabled);

    XeTa save(XeTa xeTa);

    XeTa get(Integer id) throws Exception;
}

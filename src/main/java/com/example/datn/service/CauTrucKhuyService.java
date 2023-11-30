package com.example.datn.service;


import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.exception.CauTrucKhuyNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CauTrucKhuyService {
    List<CauTrucKhuy> getAllCauTrucKhuy();
    public static final int MATERIALS_PER_PAGE = 4;

    public List<CauTrucKhuy> getAllPaginationCauTrucKhuy();
    public Page<CauTrucKhuy> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    public CauTrucKhuy save(CauTrucKhuy cauTrucKhuy);

    public CauTrucKhuy get(Integer id) throws CauTrucKhuyNotFoundException, Exception;

    public boolean checkUnique(Integer id, String ten);

    public void updateCauTrucKhuyEnabledStatus(Integer id, boolean enabled);
}

package com.example.datn.service.impl;

import com.example.datn.entity.XeTa;
import com.example.datn.repository.XetaRepository;
import com.example.datn.service.XeTaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XeTaServiceImpl implements XeTaService {
    public static final int COLORS_PER_PAGE = 10;
    @Autowired
    XetaRepository xetaRepository;
    @Override
    public List<XeTa> getAllXeTa() {
        return xetaRepository.findAll();
    }

    @Override
    public Page<XeTa> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, COLORS_PER_PAGE, sort);

        if(keyword != null){

            return xetaRepository.findAll(keyword,pageable);
        }

        return xetaRepository.findAll(pageable);
    }

    @Override
    public void updateXeTaEnabledStatus(Integer id, boolean enabled) {
    xetaRepository.updateEnabledStatus(id,enabled);
    }

    @Override
    public XeTa save(XeTa xeTa) {
        return xetaRepository.save(xeTa);
    }

    @Override
    public XeTa get(Integer id) throws Exception {
        try {
            return xetaRepository.findById(id)
                    .orElseThrow(() -> new MailAuthenticationException("Không tìm thấy danh mục nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}

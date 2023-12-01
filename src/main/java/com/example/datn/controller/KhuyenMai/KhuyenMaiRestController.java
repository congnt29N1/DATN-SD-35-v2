package com.example.datn.controller.KhuyenMai;

import com.example.datn.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KhuyenMaiRestController {
    @Autowired
    KhuyenMaiService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/discounts/check_name")
    public String checkDuplicateTenAndMa(@Param("id") Integer id, @Param("ten") String ten, @Param("ma") String ma) {
        boolean isUnique = service.checkUnique(id, ten, ma);
        return isUnique ? "OK" : "Duplicated";
    }
}

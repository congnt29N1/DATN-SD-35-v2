package com.example.datn.controller.kieudet;

import com.example.datn.service.KieuDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KieuDetRestController {
    @Autowired
    KieuDetService kieuDetService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/kieudet/check_name")
    public String checkDuplicateTen(@Param("id") Integer id, @Param("ten") String ten) {
        return kieuDetService.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}

package com.example.datn.controller.kieutui;

import com.example.datn.service.KieuTuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KieuTuiRestController {
    @Autowired
    KieuTuiService kieuTuiService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/kieutui/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return kieuTuiService.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}

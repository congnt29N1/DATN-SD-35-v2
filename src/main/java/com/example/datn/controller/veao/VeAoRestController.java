package com.example.datn.controller.veao;

import com.example.datn.service.VeAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VeAoRestController
{
 @Autowired
 VeAoService veAoService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/veao/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return veAoService.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}

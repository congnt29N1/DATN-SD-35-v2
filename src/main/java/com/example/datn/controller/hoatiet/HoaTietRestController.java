package com.example.datn.controller.hoatiet;

import com.example.datn.service.HoaTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HoaTietRestController {
    @Autowired
    HoaTietService service;
    @Autowired
    HttpServletRequest  request;
    @PostMapping("/admin/degins/check_name")
    public String checkDuplicateTen(@Param("idHoaTiet") Integer id ,@Param("tenHoaTiet") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";}


}

package com.example.datn.controller.nhanvien;

import com.example.datn.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NhanVienRestController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @PostMapping("/admin/users/check_email")
    public String checkDuplicateEmail(@Param("id")Integer id,@Param("email")String email){
        return nhanVienService.isEmailUnique(id,email)?"OK":"Duplicate";
    }
}

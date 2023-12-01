package com.example.datn.controller.chatlieu;


import com.example.datn.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChatLieuRestController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private ChatLieuService chatLieuService;
    @PostMapping("/admin/chatlieu/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return chatLieuService.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}

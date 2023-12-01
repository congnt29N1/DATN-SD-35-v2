package com.example.datn.controller.khachhang;


import com.example.datn.entity.KhachHang;
import com.example.datn.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService service;
    @Autowired
    HttpServletRequest request;


    @GetMapping("/admin/customers")
    public String listFirstPage(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        return listByPage(1, model, "tenKhachHang", "asc", null);
    }

    @GetMapping("/admin/customers/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        Page<KhachHang> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<KhachHang> listKhachHang = page.getContent();
        long startCount = (pageNum - 1) * KhachHangService.CUSTOMERS_PER_PAGE + 1;
        long endCount = startCount + KhachHangService.CUSTOMERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listKhachHang", listKhachHang);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/khachhang/customers";

    }

}

package com.example.datn.controller.hoatiet;


import com.example.datn.entity.HoaTiet;
import com.example.datn.export.HoaTietCsvExporter;
import com.example.datn.export.HoaTietExcelExporter;
import com.example.datn.exception.HoaTietNotFoundException;
import com.example.datn.service.HoaTietService;
import com.example.datn.service.impl.HoaTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class HoaTietController {
    @Autowired
    HoaTietService service;
    @Autowired
    HttpServletRequest  request;

    @GetMapping("/admin/design")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();

//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        return listByPage(1,model,"tenHoaTiet","asc",null);
    }

    @GetMapping("/admin/design/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
                             @Param("keyword")String keyword
    ){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        System.out.println("SortField: " + sortField);
        System.out.println("sortOrder: " + sortDir);
        Page<HoaTiet> page = service.listByPage(pageNum, sortField, sortDir,keyword);
        List<HoaTiet> listHoaTiet= page.getContent();

        long startCount = (pageNum -1) * HoaTietServiceImpl.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + HoaTietServiceImpl.CATEGORIES_PER_PAGE-1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listHoaTiet",listHoaTiet);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/hoatiet/design";

    }

    @GetMapping("/admin/design/{id}/enabled/{status}")
    public String updateHoaTietEnabledStatus(@PathVariable("idHoaTiet") Integer id,
                                             @PathVariable("status") boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//
//
//        }
        service.updateHoaTietEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Hoạ tiết có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/design";
    }

    @GetMapping("/admin/design/new")
    public String newHoaTiet(Model model){
        HttpSession session = request.getSession();
//        if (session.getAttribute("admin") == null) {
//            return "redirect:/login-admin";
//        }
        model.addAttribute("hoaTiet", new HoaTiet());
        model.addAttribute("pageTitle","Tạo Mới Họa Tiết");
        return "admin/hoatiet/design_form";
    }

    @PostMapping("/admin/design/save")
    public String saveHoaTiet(HoaTiet danhMuc, RedirectAttributes redirectAttributes){
//        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        service.save(danhMuc);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/design";
    }

    @GetMapping("/admin/design/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession();
//            if(session.getAttribute("admin") == null ){
//                return "redirect:/login-admin" ;
//            }
            HoaTiet hoaTiet = service.get(id);
            model.addAttribute("hoaTiet", hoaTiet);
            model.addAttribute("pageTitle","Update Họa Tiết(ID : " + id + ")");
            return "admin/hoatiet/design_form";
        }catch (HoaTietNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/admin/design";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }

    }

    @GetMapping("/admin/design/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        List<HoaTiet> listHoaTiet = service.getAllHoaTiet();
        HoaTietCsvExporter exporter = new HoaTietCsvExporter();
        exporter.export(listHoaTiet,response);
    }

    @GetMapping("/admin/design/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<HoaTiet> listHoaTiet = service.getAllHoaTiet();
        HoaTietExcelExporter exporter = new HoaTietExcelExporter();
        exporter.export(listHoaTiet,response);

    }

}

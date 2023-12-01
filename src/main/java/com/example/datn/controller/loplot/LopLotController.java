package com.example.datn.controller.loplot;


import com.example.datn.entity.LopLot;
import com.example.datn.exception.LopLotNotFoundException;
import com.example.datn.export.LopLotCsvExporter;
import com.example.datn.export.LopLotExcelExporter;
import com.example.datn.service.LopLotService;
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
public class LopLotController {
    @Autowired
    LopLotService lopLotService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/loplot")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenLopLot","asc",null);
    }

    @GetMapping("/admin/loplot/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<LopLot> page = lopLotService.listByPage(pageNum,sortField,sortDir,keyword);
        List<LopLot> listLopLot = page.getContent();
        long startCount = (pageNum -1) * LopLotService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + LopLotService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listLopLot",listLopLot);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/loplot/loplot";

    }

    @GetMapping("/admin/loplot/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        lopLotService.updateLopLotEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Lớp lót có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/loplot";
    }

    @GetMapping("/admin/loplot/new")
    public String newVatLieu(Model model){
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        model.addAttribute("lopLot",new LopLot());
        model.addAttribute("pageTitle","Tạo Mới Lớp lót");
        return "admin/loplot/loplot_form";
    }

    @PostMapping("/admin/loplot/save")
    public String saveVatLieu(LopLot lopLot, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        lopLotService.save(lopLot);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/loplot";
    }

    @GetMapping("/admin/loplot/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try {
            HttpSession session = request.getSession();
//            if(session.getAttribute("admin") == null ){
//                return "redirect:/login-admin" ;
//            }
            LopLot lopLot = lopLotService.get(id);
            model.addAttribute("lopLot", lopLot);
            model.addAttribute("pageTitle", "Update lớp lót (ID: " + id + ")");
            return "admin/loplot/loplot_form";
        } catch (LopLotNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/loplot";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/loplot/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<LopLot> listLopLot = lopLotService.getAllLopLot();
        LopLotCsvExporter exporter = new LopLotCsvExporter();
        exporter.export(listLopLot,response);
    }

    @GetMapping("/admin/loplot/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<LopLot> listLopLot = lopLotService.getAllLopLot();
        LopLotExcelExporter exporter = new LopLotExcelExporter();
        exporter.export(listLopLot,response);
    }
}

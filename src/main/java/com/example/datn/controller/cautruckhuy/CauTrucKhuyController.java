package com.example.datn.controller.cautruckhuy;


import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.exception.CauTrucKhuyNotFoundException;
import com.example.datn.export.CauTrucKhuyExcelExporter;
import com.example.datn.export.CautrucKhuyCsvExporter;
import com.example.datn.service.CauTrucKhuyService;
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
public class CauTrucKhuyController {
    @Autowired
    CauTrucKhuyService cauTrucKhuyService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/cautruckhuy")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenCauTrucKhuy","asc",null);
    }

    @GetMapping("/admin/cautruckhuy/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<CauTrucKhuy> page = cauTrucKhuyService.listByPage(pageNum,sortField,sortDir,keyword);
        List<CauTrucKhuy> listCauTrucKhuy = page.getContent();
        long startCount = (pageNum -1) * CauTrucKhuyService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + CauTrucKhuyService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listCauTrucKhuy",listCauTrucKhuy);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/cautruckhuy/cautruckhuy";

    }

    @GetMapping("/admin/cautruckhuy/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        cauTrucKhuyService.updateCauTrucKhuyEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Cấu trúc khuy có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/cautruckhuy";
    }

    @GetMapping("/admin/cautruckhuy/new")
    public String newVatLieu(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("cauTrucKhuy",new CauTrucKhuy());
        model.addAttribute("pageTitle","Tạo Mới cấu trúc khuy");
        return "admin/cautruckhuy/cautruckhuy_form";
    }

    @PostMapping("/admin/cautruckhuy/save")
    public String saveVatLieu(CauTrucKhuy cauTrucKhuy, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        cauTrucKhuyService.save(cauTrucKhuy);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/cautruckhuy";
    }

    @GetMapping("/admin/cautruckhuy/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            CauTrucKhuy cauTrucKhuy = cauTrucKhuyService.get(id);
            model.addAttribute("cauTrucKhuy", cauTrucKhuy);
            model.addAttribute("pageTitle", "Update Cấu trúc khuy (ID: " + id + ")");
            return "admin/cautruckhuy/cautruckhuy_form";
        } catch (CauTrucKhuyNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/cautruckhuy";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/cautruckhuy/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<CauTrucKhuy> listCauTrucKhuy = cauTrucKhuyService.getAllCauTrucKhuy();
        CautrucKhuyCsvExporter exporter = new CautrucKhuyCsvExporter();
        exporter.export(listCauTrucKhuy,response);
    }

    @GetMapping("/admin/cautruckhuy/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<CauTrucKhuy> listVatLieu = cauTrucKhuyService.getAllCauTrucKhuy();
        CauTrucKhuyExcelExporter exporter = new CauTrucKhuyExcelExporter();
        exporter.export(listVatLieu,response);
    }
}

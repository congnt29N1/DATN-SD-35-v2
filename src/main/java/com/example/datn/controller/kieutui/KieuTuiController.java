package com.example.datn.controller.kieutui;

import com.example.datn.entity.KieuTui;
import com.example.datn.exception.kieuTuiNotFoundException;
import com.example.datn.export.KieuTuiCsvExporter;
import com.example.datn.export.KieuTuiExcelExporter;
import com.example.datn.service.KieuTuiService;
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
public class KieuTuiController {
    @Autowired
    KieuTuiService kieuTuiService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/kieutui")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenKieuTui","asc",null);
    }
    @GetMapping("/admin/kieutui/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<KieuTui> page = kieuTuiService.listByPage(pageNum,sortField,sortDir,keyword);
        List<KieuTui> listKieuTui = page.getContent();
        long startCount = (pageNum -1) * KieuTuiService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + kieuTuiService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listKieuTui",listKieuTui);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/kieutui/kieutui";

    }
    @GetMapping("/admin/kieutui/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        kieuTuiService.updateKieuTuiEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Kiểu túi có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/kieutui";
    }

    @GetMapping("/admin/kieutui/new")
    public String newVatLieu(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("kieuTui",new KieuTui());
        model.addAttribute("pageTitle","Tạo Mới kiểu túi");
        return "admin/kieutui/kieutui_form";
    }

    @PostMapping("/admin/kieutui/save")
    public String saveVatLieu(KieuTui kieuTui, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        kieuTuiService.save(kieuTui);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/kieutui";
    }

    @GetMapping("/admin/kieutui/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            KieuTui kieuTui = kieuTuiService.get(id);
            model.addAttribute("kieuTui", kieuTui);
            model.addAttribute("pageTitle", "Update kiểu túi (ID: " + id + ")");
            return "admin/kieutui/kieutui_form";
        } catch (kieuTuiNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/kieutui";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/kieutui/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<KieuTui> listKieuTui = kieuTuiService.getAllKieuTui();
        KieuTuiCsvExporter exporter = new KieuTuiCsvExporter();
        exporter.export(listKieuTui,response);
    }

    @GetMapping("/admin/kieutui/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<KieuTui> listKieuTui = kieuTuiService.getAllKieuTui();
        KieuTuiExcelExporter exporter = new KieuTuiExcelExporter();
        exporter.export(listKieuTui,response);
    }
}

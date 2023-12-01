package com.example.datn.controller.kieudet;

import com.example.datn.entity.KieuDet;
import com.example.datn.exception.KieuDetNotFoundException;
import com.example.datn.export.KieuDetCsvExporter;
import com.example.datn.export.KieuDetExcelExporter;
import com.example.datn.service.KieuDetService;
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
public class KieuDetController {
    @Autowired
    KieuDetService kieuDetService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/kieudet")
    public String listFirstPage(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        return listByPage(1, model, "tenKieuDet", "asc", null);
    }

    @GetMapping("/admin/kieudet/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }

        Page<KieuDet> page = kieuDetService.listByPage(pageNum, sortField, sortDir, keyword);
        List<KieuDet> listKieuDet = page.getContent();
        long startCount = (pageNum - 1) * KieuDetService.MATERIALS_PER_PAGE + 1;
        long endCount = startCount + KieuDetService.MATERIALS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listKieuDet", listKieuDet);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/kieudet/kieudet";

    }

    @GetMapping("/admin/kieudet/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status") boolean enabled,
                                             RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        kieuDetService.updateKieuDetEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Vật liệu có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/kieudet";
    }

    @GetMapping("/admin/kieudet/new")
    public String newVatLieu(Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        model.addAttribute("kieuDet", new KieuDet());
        model.addAttribute("pageTitle", "Tạo Mới kiểu dệt");
        return "admin/kieudet/kieudet_form";
    }

    @PostMapping("/admin/kieudet/save")
    public String saveVatLieu(KieuDet kieuDet, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return "redirect:/login-admin";
        }
        kieuDetService.save(kieuDet);
        redirectAttributes.addFlashAttribute("message", "Thay Đổi Thành Công");
        return "redirect:/admin/kieudet";
    }

    @GetMapping("/admin/kieudet/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            KieuDet kieuDet = kieuDetService.get(id);
            model.addAttribute("kieuDet", kieuDet);
            model.addAttribute("pageTitle", "Update Kiểu dệt (ID: " + id + ")");
            return "admin/kieudet/kieudet_form";
        } catch (KieuDetNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/materials";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/kieudet/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<KieuDet> listKieuDet = kieuDetService.getAllKieuDet();
        KieuDetCsvExporter exporter = new KieuDetCsvExporter();
        exporter.export(listKieuDet, response);
    }

    @GetMapping("/admin/kieudet/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<KieuDet> listKieuDet = kieuDetService.getAllKieuDet();
        KieuDetExcelExporter exporter = new KieuDetExcelExporter();
        exporter.export(listKieuDet, response);
    }
}

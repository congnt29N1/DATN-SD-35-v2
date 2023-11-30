package com.example.datn.controller.veao;


import com.example.datn.entity.VeAo;
import com.example.datn.exception.VeAoNotFoundException;
import com.example.datn.export.VeAoCsvExporter;
import com.example.datn.export.VeAoExcelExporter;
import com.example.datn.service.VeAoService;
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
public class VeAoController {
    @Autowired
    VeAoService veAoService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/veao")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenVeAo","asc",null);
    }

    @GetMapping("/admin/veao/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<VeAo> page = veAoService.listByPage(pageNum,sortField,sortDir,keyword);
        List<VeAo> listVeAo = page.getContent();
        long startCount = (pageNum -1) * VeAoService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + VeAoService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listVeAo",listVeAo);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/veao/veao";

    }

    @GetMapping("/admin/veao/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        veAoService.updateVeAoEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Ve áo có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/veao";
    }

    @GetMapping("/admin/veao/new")
    public String newVatLieu(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("veAo",new VeAo());
        model.addAttribute("pageTitle","Tạo Mới ve áo");
        return "admin/veao/veao_form";
    }

    @PostMapping("/admin/veao/save")
    public String saveVatLieu(VeAo veao, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        veAoService.save(veao);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/veao";
    }

    @GetMapping("/admin/veao/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") == null) {
                return "redirect:/login-admin";
            }
            VeAo veao = veAoService.get(id);
            model.addAttribute("veAo", veao);
            model.addAttribute("pageTitle", "Update Ve áo(ID: " + id + ")");
            return "admin/veao/veao_form";
        } catch (VeAoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/veao";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/veao/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<VeAo> listVatLieu = veAoService.getAllVeAo();
        VeAoCsvExporter exporter = new VeAoCsvExporter();
        exporter.export(listVatLieu,response);
    }

    @GetMapping("/admin/veao/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<VeAo> listVeAo = veAoService.getAllVeAo();
        VeAoExcelExporter exporter = new VeAoExcelExporter();
        exporter.export(listVeAo,response);
    }
}

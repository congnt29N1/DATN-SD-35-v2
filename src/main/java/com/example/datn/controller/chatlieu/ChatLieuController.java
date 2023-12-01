package com.example.datn.controller.chatlieu;


import com.example.datn.entity.ChatLieu;
import com.example.datn.exception.ChatLieuNotFoundException;
import com.example.datn.export.ChatLieuCsvExporter;
import com.example.datn.export.ChatLieuExcelExporter;
import com.example.datn.service.ChatLieuService;
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
public class ChatLieuController {
    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    HttpServletRequest request;
    @GetMapping("/admin/chatlieu")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        return listByPage(1,model,"tenChatLieu","asc",null);
    }

    @GetMapping("/admin/chatlieu/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }

        Page<ChatLieu> page = chatLieuService.listByPage(pageNum,sortField,sortDir,keyword);
        List<ChatLieu> listChatLieu = page.getContent();
        long startCount = (pageNum -1) * ChatLieuService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + ChatLieuService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listChatLieu",listChatLieu);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/chatlieu/chatlieu";

    }

    @GetMapping("/admin/chatlieu/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        chatLieuService.updateChatLieuEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Chất liệu có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/chatlieu";
    }

    @GetMapping("/admin/chatlieu/new")
    public String newChatLieu(Model model){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        model.addAttribute("chatLieu",new ChatLieu());
        model.addAttribute("pageTitle","Tạo Mới Chất Liệu");
        return "admin/chatlieu/chatlieu_form";
    }

    @PostMapping("/admin/chatlieu/save")
    public String saveChatLieu(ChatLieu chatLieu, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
//        if(session.getAttribute("admin") == null ){
//            return "redirect:/login-admin" ;
//        }
        chatLieuService.save(chatLieu);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/chatlieu";
    }

    @GetMapping("/admin/chatlieu/edit/{id}")
    public String editChatLieu(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try {
            HttpSession session = request.getSession();
//           if (session.getAttribute("admin") == null) {
////                return "redirect:/login-admin";
////            }
            ChatLieu chatLieu = chatLieuService.get(id);
            model.addAttribute("chatLieu", chatLieu);
            model.addAttribute("pageTitle", "Update Chất Liệu (ID: " + id + ")");
            return "admin/chatlieu/chatlieu_form";
        } catch (ChatLieuNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/chatlieu";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/chatlieu/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<ChatLieu> listChatLieu = chatLieuService.getAllChatLieu();
        ChatLieuCsvExporter exporter = new ChatLieuCsvExporter();
        exporter.export(listChatLieu,response);
    }

    @GetMapping("/admin/chatlieu/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ChatLieu> listChatLieu = chatLieuService.getAllChatLieu();
        ChatLieuExcelExporter exporter = new ChatLieuExcelExporter();
        exporter.export(listChatLieu,response);
    }
}

package com.example.datn.controller.xeta;
import com.example.datn.entity.XeTa;
import com.example.datn.exception.XeTaNotFoundException;
import com.example.datn.service.XeTaService;
import com.example.datn.service.impl.XeTaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/xeta")
public class XeTaController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    XeTaService xeTaService;
    @GetMapping
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenXeTa","asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
                             @Param("keyword")String keyword
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Page<XeTa> page = xeTaService.listByPage(pageNum, sortField, sortDir,keyword);
        List<XeTa> listXeta = page.getContent();

        long startCount = (pageNum -1) * XeTaServiceImpl.COLORS_PER_PAGE + 1;
        long endCount = startCount + XeTaServiceImpl.COLORS_PER_PAGE - 1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listXeta",listXeta);
        model.addAttribute("sortField", "tenXeTa");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/xeta/xeta";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateXeTaEnabledStatus(@PathVariable("id") Integer id,
                                            @PathVariable("status") boolean enabled,
                                            RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        xeTaService.updateXeTaEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "xẻ tà có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/xeta";
    }

    @GetMapping("/new")
    public String newXeTa(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("xeTa", new XeTa());
        model.addAttribute("pageTitle","Tạo mới");
        return "admin/xeta/xeta_form";
    }

    @PostMapping("/save")
    public String saveXeTa(XeTa xeTa, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        xeTaService.save(xeTa);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/xeta";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            XeTa xeTa = xeTaService.get(id);
            model.addAttribute("xeTa", xeTa);
            model.addAttribute("pageTitle","Cập nhật");
            return "admin/xeta/xeta_form";
        } catch (XeTaNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/admin/xeta";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/admin/error";
        }
    }
}

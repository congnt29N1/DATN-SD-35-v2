package com.example.datn.controller;

import com.example.datn.entity.NhanVien;
import com.example.datn.repository.NhanVienRepository;
import com.example.datn.request.LoginAdminRequest;
import com.example.datn.security.AccountFilterService;
import com.example.datn.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MainController {

@Autowired
AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    NhanVienService nhanVienService;
    @GetMapping("/admin")
    public String home(){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("admin")==null){
            return "redirect:/login-admin";
        }
        return "admin/index";
    }
    @GetMapping("/login-admin")
    public String formLongin(){return "admin/login";}
    @GetMapping("admin/logout")
    public String logout(){
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("admin");
        return "redirect:/login-admin";
    }
    @PostMapping("/post/login")
    public ModelAndView authenticateUser(@Valid LoginAdminRequest loginAdminRequest, Model model) throws Exception{
        HttpSession session = httpServletRequest.getSession();
        try{
            NhanVien userEntity = nhanVienRepository.getNhanVienByEmail(loginAdminRequest.getEmail());

            if (passwordEncoder.matches(loginAdminRequest.getPassword(),userEntity.getMatKhau())){
//            if (userEntity.getMatKhau().equals(loginAdminRequest.getPassword())){
                session.setAttribute("admin",userEntity);
                ModelAndView modelAndView = new ModelAndView("redirect:/admin");
                return modelAndView;
            }else {
                model.addAttribute("error","Đăng nhập không thành công. Hãy kiểm tra lại email và mật khẩu.");
                return new ModelAndView("admin/login");
            }
        }catch (Exception ex){
            System.out.println(ex);
            model.addAttribute("error","Đăng nhập thất bại");
            return new ModelAndView("admin/login");
        }
    }
    public Authentication authenticate(String username,String password) throws Exception{
        try{
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}

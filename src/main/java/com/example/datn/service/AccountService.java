package com.example.datn.service;

import com.example.datn.entity.KhachHang;
import com.example.datn.request.ChangePassRequest;
import com.example.datn.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

public interface AccountService {
    ResponseEntity<?> register (RegisterRequest registerRequest) throws Exception;
    HashMap<Integer,String> getListTP();
    HashMap<Integer,String> getListQuan( Integer idTP) throws Exception;
    HashMap<String,String> getListPhuong( Integer idQH) throws  Exception;
    ResponseEntity<?> changePass (Principal p ,ChangePassRequest changePassRequest) throws Exception;
     void createPasswordResetTokenForUser(KhachHang user, String token);

     String validatePasswordResetToken(String token);

     Optional<KhachHang> getUserByPasswordResetToken(final String token);

     void changeforgotPass(KhachHang khachHang, String pass);
}

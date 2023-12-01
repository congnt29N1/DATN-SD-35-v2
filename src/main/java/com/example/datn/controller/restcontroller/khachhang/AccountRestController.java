package com.example.datn.controller.restcontroller.khachhang;

import com.example.datn.entity.KhachHang;
import com.example.datn.exception.ErrorResponse;
import com.example.datn.exception.GenericResponse;
import com.example.datn.repository.KhachHangRepository;
import com.example.datn.request.ChangePassForgetRequest;
import com.example.datn.request.ChangePassRequest;
import com.example.datn.request.LoginRequest;
import com.example.datn.request.RegisterRequest;
import com.example.datn.response.LoginResponse;
import com.example.datn.security.AccountFilterService;
import com.example.datn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AccountRestController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;
    @Autowired
    private Environment env;
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender javaMailSender;


    public Authentication authenticate(String username, String password) throws Exception {
        try {
          return   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @Valid @RequestBody LoginRequest loginRequest) {
                try {
                Authentication authentication = authenticate(
                                loginRequest.getUsername(), loginRequest.getPassword()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                KhachHang userEntity = khachHangRepository.getKhachHangByEmail(authentication.getName());
                String jwt = tokenProvider.generateToken(authentication);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwt);
                loginResponse.setStatus(200);
                loginResponse.setIdKhachHang(userEntity.getIdKhachHang());
                loginResponse.setMessage("Đăng nhập thành công");
                loginResponse.setUsername(userEntity.getEmail());
                    this.session.setAttribute("userName",userEntity.getUsername());
                return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
            } catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Sai mật khẩu hoặc email "));
            }
    }

    @PostMapping("/register")
    public ResponseEntity<?> singup( @Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        return accountService.register(registerRequest);
    }

    @GetMapping("/getTP")
    public ResponseEntity<?> getTP() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListTP());
    }

    @GetMapping("/getQH/{idTinh}")
    public ResponseEntity<?> getQH(@PathVariable("idTinh") Integer idTinh) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListQuan(idTinh));
    }
    @GetMapping("/getPX/{idQuan}")
    public ResponseEntity<?> getPX(@PathVariable("idQuan") Integer idQuan) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListPhuong(idQuan));
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass( @Valid @RequestBody ChangePassRequest changePassRequest,Principal p ) throws Exception {
            return accountService.changePass(p, changePassRequest);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        KhachHang user = khachHangRepository.getKhachHangByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Email không tồn tại"));
        }
        String token = UUID.randomUUID().toString();
        accountService.createPasswordResetTokenForUser(user, token);
        javaMailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
    }


    @GetMapping("/user/changePassword/{token}")
    public ResponseEntity<?> showChangePasswordPage(@PathVariable("token") String token) {
        String result = accountService.validatePasswordResetToken(token);
        if(result != null) {
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"het han"));
        } else {
            return  ResponseEntity.status(200).body(new ErrorResponse(HttpStatus.OK,"oke"));
        }
    }

    @PostMapping("/updatePass/{token}")
    public ResponseEntity<?> changePassForgot(@RequestBody ChangePassForgetRequest changePassForgetRequest, @PathVariable("token") String token ) throws Exception {
        final String result = accountService.validatePasswordResetToken(token);
        if(result != null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Mã đã hết hạn"));
        }

        Optional<KhachHang> user = accountService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            if(!changePassForgetRequest.getNewPass().equals(changePassForgetRequest.getConfirmPass())){
                return  ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Xác nhận mật khẩu không khớp"));
            }
            accountService.changeforgotPass(user.get(), changePassForgetRequest.getNewPass());
            return  ResponseEntity.status(200).body(new ErrorResponse(HttpStatus.OK,"Cập nhập thành công"));
        } else {
            return  ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"K thấy người dùng"));
        }
    }
    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, String token, KhachHang user) {
        String url = contextPath + "/index#/changePassword/" + token;
        String message = "Hi "+ user.getTenKhachHang();
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             KhachHang user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }



}

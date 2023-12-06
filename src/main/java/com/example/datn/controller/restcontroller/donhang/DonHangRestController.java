package com.example.datn.controller.restcontroller.donhang;


import com.example.datn.request.DonHangRequest;
import com.example.datn.response.DonHangResponse;
import com.example.datn.response.HoaDonChiTietResponse;
import com.example.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/api/don-hang")
public class DonHangRestController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    DonHangService donHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    HttpServletRequest request;


  @GetMapping("/findAll/{idKhachHang}")
    public ResponseEntity<?> getAllDH(@PathVariable("idKhachHang") Integer idKhachHang) {
        try {
            List<DonHangResponse> responseList = donHangService.findAllHD(idKhachHang);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
  }
    @GetMapping("/findByStatus/{idKhachHang}")
    public ResponseEntity<?> getDHbyStatus(@PathVariable("idKhachHang") Integer idKhachHang, @PathParam("status") Integer status) {
        try {
            List<DonHangResponse> responseList = donHangService.findHDByStatus(idKhachHang,status);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/findHDCT/{idDonHang}")
    public ResponseEntity<?> findHDCT(@PathVariable("idDonHang") Integer idDonHang) {
        try {
            List<HoaDonChiTietResponse> responseList = donHangService.findHDCTbyDH(idDonHang);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDH(@RequestBody DonHangRequest donHangRequest){
        return ResponseEntity.status(HttpStatus.OK).body(donHangService.updateDH(donHangRequest));
    }

}

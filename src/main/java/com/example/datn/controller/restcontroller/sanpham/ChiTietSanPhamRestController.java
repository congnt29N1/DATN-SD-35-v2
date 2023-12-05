package com.example.datn.controller.restcontroller.sanpham;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.service.ChiTietSanPhamService;
import com.example.datn.service.MaDinhDanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/chi-tiet-san-pham")
public class ChiTietSanPhamRestController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
   @Autowired
   MaDinhDanhService maDinhDanhService;

    @PutMapping("/update")
    ResponseEntity<?> update(ChiTietSanPham chiTietSanPham){
        return ResponseEntity.status(HttpStatus.OK).body(chiTietSanPhamService.update(chiTietSanPham));
    }

    @GetMapping("/countMaDinhDanh/{idChiTietSanPham}")
    ResponseEntity<?> countSeri(@PathVariable("idChiTietSanPham") Integer idChiTietSanPham){
        return ResponseEntity.status(HttpStatus.OK).body(maDinhDanhService.countMaDinhDanh(idChiTietSanPham));
    }
}

package com.example.datn.controller.restcontroller;


import com.example.datn.response.ChiTietSanPhamResponse;
import com.example.datn.response.SanPhamDetailResponse;
import com.example.datn.response.TrangChuResponse;
import com.example.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin("*")
@Controller
@RequestMapping("/api/index")
public class TrangChuRestController {
    @Autowired
    SanPhamService sanPhamService;

    @ResponseBody
    @GetMapping("")
   public ResponseEntity<?> home(){
         List<SanPhamDetailResponse> listSPbanChay = sanPhamService.getSPchay();
        List<SanPhamDetailResponse> listSPmoiNhat = sanPhamService.getSPnew();
        List<SanPhamDetailResponse> listSPnoiBat = sanPhamService.getSPFeature();
        TrangChuResponse trangChuResponse = new TrangChuResponse(listSPbanChay,listSPmoiNhat,listSPnoiBat);
        return ResponseEntity.status(HttpStatus.OK).body(trangChuResponse);
    }

    @ResponseBody
    @GetMapping("/getSPKM")
    public ResponseEntity<?> getSPKM(@PathParam("idCTSP") Integer idCTSP){
        List<ChiTietSanPhamResponse> chiTietSanPhamResponseList = sanPhamService.getSPchayKM(idCTSP);
        return ResponseEntity.status(HttpStatus.OK).body(chiTietSanPhamResponseList);
    }
}

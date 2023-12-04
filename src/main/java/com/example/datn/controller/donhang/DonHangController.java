package com.example.datn.controller.donhang;

import com.example.datn.cache.DiaChiCache;
import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.entity.MaDinhDanhCTSP;
import com.example.datn.giaohangnhanhservice.DiaChiAPI;
import com.example.datn.giaohangnhanhservice.DonHangAPI;
import com.example.datn.giaohangnhanhservice.request.ChiTietItemRequestGHN;
import com.example.datn.giaohangnhanhservice.request.TaoDonHangRequestGHN;
import com.example.datn.service.*;
import com.example.datn.utils.TrangThaiDonHang;
import com.example.datn.utils.TrangThaiMaDinhDanh;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;


@RequestMapping("")
@Controller
@Slf4j
public class DonHangController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    private DonHangService donHangService;

    @Autowired
    HttpServletRequest request;
    @Autowired
    ChiTietSanPhamService ctspService;
    @Autowired
    MaDinhDanhService maDinhDanhService;

    private List<ChiTietItemRequestGHN> toListChiTietItem(List<HoaDonChiTiet> listHDCT) {
        List<ChiTietItemRequestGHN> result = new ArrayList<>();
        listHDCT.forEach(hdct -> {
            ChiTietItemRequestGHN item = ChiTietItemRequestGHN.builder()
                    .giaBan(hdct.getGiaBan())
                    .soLuong(hdct.getSoLuong())
                    .ctsp(hdct.getChiTietSanPham())
                    .name(hdct.getChiTietSanPham().getSanPham().getTenSanPham())
                    .build();
            result.add(item);
        });
        return result;
    }

    @GetMapping("/don-hang/thong-tin-thanh-toan")
    public RedirectView thongTinThanhToan() {

        try {


            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            DonHang donhang = donHangService.getById(Integer.valueOf(request.getParameter("vnp_TxnRef")));
            if (donhang != null) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    System.out.println("Thanh toán thành công ");
                    donhang.setTrangThaiDonHang(TrangThaiDonHang.CHO_XAC_NHAN);
                    donHangService.save(donhang);
                    return new RedirectView("http://localhost:8080/index#/success");
                } else {
                    hdctService.xoaHDCTByIdDonHang(donhang);
                    donHangService.xoaDonHang(donhang);
                    System.out.println("Không thành công");
                    return new RedirectView("http://localhost:8080/index#/fail");
                }
            } else {
                System.out.println("Không tìm thấy order");
                return new RedirectView("http://localhost:8080/index#/fail");
            }
        } catch (Exception e) {
            System.out.println("k xdc");
            return new RedirectView("http://localhost:8080/index#/fail");
        }
    }

    private TaoDonHangRequestGHN createGHNRequest(DonHang donhang) {
        try {
            List<HoaDonChiTiet> listHDCT = hdctService.getByHoaDonId(donhang);
            Integer soLuong = 0;
            for (HoaDonChiTiet item : listHDCT) {
                soLuong += item.getSoLuong();
            }
            TaoDonHangRequestGHN requestGHN = TaoDonHangRequestGHN.builder()
                    .note(donhang.getGhiChu())
                    .toName(donhang.getKhachHang().getTenKhachHang())
                    .toPhone(donhang.getKhachHang().getSoDienThoai())
                    .toAddress(donhang.getDiaChi())
                    .idQuanHuyen(donhang.getIdQuanHuyen())
                    .idPhuongXa(donhang.getIdPhuongXa())
                    .soLuongSanPham(soLuong)
                    .listItems(toListChiTietItem(listHDCT))
                    .phuongThuc(donhang.getPhuongThuc())
                    .thanhTien(donhang.getTongTien())
                    .build();
            return requestGHN;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/admin/don-hang")
    public String getForm(Model model,
                          HttpSession httpSession) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return findAll(1, model,httpSession);
    }

    @GetMapping("/admin/don-hang/page/{pageNum}")
    public String findAll(
            @PathVariable("pageNum") int pageNum,
            Model model,
            HttpSession httpSession
    ) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Page<DonHang> donHangs = donHangService.getAll(pageNum);

        model.addAttribute("list", donHangs.getContent());

        model.addAttribute("diaChiCache", new DiaChiCache());
        model.addAttribute("diaChiAPI", new DiaChiAPI());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", donHangs.getTotalPages());

//        httpSession.setAttribute("listDonHang",donHangs.getContent());

        return "admin/donhang/donhang";
    }

    @GetMapping("/admin/don-hang/update/{id}/trang-thai/{trangThai}")
    public String updateStatusDonHang(
            HttpSession session,
            @PathVariable("trangThai") int trangThai,
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        HttpSession sessionn = request.getSession();
        System.out.println(sessionn.getAttribute("admin") +"co sesion r");
        if(sessionn.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        DonHang donHang = donHangService.findById(id);
        if(trangThai == TrangThaiDonHang.DANG_CHUAN_BI){
            try {
                TaoDonHangRequestGHN donHangRequestGHN = createGHNRequest(donHang);
                Integer code = DonHangAPI.createOrder(donHangRequestGHN);
//                if(code != 200){
//                    log.error("Lỗi gửi Giao Hàng nhanh code {}", code);
//                    redirectAttributes.addFlashAttribute("error","Lỗi hệ thống giao hàng nhanh");
//                    return "redirect:/admin/don-hang";
//                }
                donHang.setTrangThaiDonHang(trangThai);
                donHang.setNgayCapNhap(new Date());
//                donHang.setNgayGiaoHang(new Date());
            } catch (Exception e) {
                log.error("Lỗi gửi Giao Hàng nhanh {}", e);
                redirectAttributes.addFlashAttribute("error","Lỗi request giao hàng nhanh");
                return "redirect:/admin/don-hang";
            }
        }else if(trangThai == TrangThaiDonHang.DANG_GIAO){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());
            List<HoaDonChiTiet> listHDCT = donHang.getListHoaDonChiTiet();
            listHDCT.forEach(hdct->{
                ChiTietSanPham ctsp = hdct.getChiTietSanPham();
                Integer soLuong = hdct.getSoLuong();
                List<MaDinhDanhCTSP> listMDD = maDinhDanhService.findByChiTietSanPham(ctsp,soLuong);
                listMDD.forEach(mdd -> {
                    mdd.setTrangThai(TrangThaiMaDinhDanh.DANG_GIAO);
                    mdd.setHoaDonChiTiet(hdct);
                });
                maDinhDanhService.saveMany(listMDD);
                hdct.setListMDD(listMDD);
            });
            hdctService.saveAll(listHDCT);
        }else if(trangThai == TrangThaiDonHang.HOAN_THANH){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());

            List<HoaDonChiTiet> listHDCT = donHang.getListHoaDonChiTiet();
            listHDCT.forEach(hdct->{
                ChiTietSanPham ctsp = hdct.getChiTietSanPham();
                Integer soLuong = hdct.getSoLuong();
                List<MaDinhDanhCTSP> listMDD = maDinhDanhService.findByHoaDonChiTiet(hdct.getIdHoaDonChiTiet());
                listMDD.forEach(mdd -> {
                    mdd.setTrangThai(TrangThaiMaDinhDanh.DA_BAN);
                    mdd.setNgayBan(new Timestamp(new Date().getTime()));
                    mdd.setHoaDonChiTiet(hdct);
                });
                System.out.println(listMDD+"lisssssssssssssssssss");
                maDinhDanhService.saveMany(listMDD);
            });
            hdctService.saveAll(listHDCT);
        }else if(trangThai == TrangThaiDonHang.DA_HUY){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());
            List<HoaDonChiTiet> listHDCT = donHang.getListHoaDonChiTiet();
            listHDCT.forEach(hdct->{
                ChiTietSanPham ctsp = hdct.getChiTietSanPham();
                Integer soLuong = hdct.getSoLuong();
                List<MaDinhDanhCTSP> listMDD = maDinhDanhService.findByChiTietSanPham(ctsp,soLuong);
                listMDD.forEach(mdd -> {
                    mdd.setTrangThai(TrangThaiMaDinhDanh.CHUA_BAN);
                    mdd.setNgayBan(null);
                    mdd.setHoaDonChiTiet(null);
                });
                maDinhDanhService.saveMany(listMDD);
            });
            hdctService.saveAll(listHDCT);
        }else if(trangThai == TrangThaiDonHang.YEU_CAU_HOAN_TRA){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());
        }else if(trangThai == TrangThaiDonHang.DA_HOAN_TRA){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());
            List<HoaDonChiTiet> listHDCT = donHang.getListHoaDonChiTiet();
            listHDCT.forEach(hdct->{
                ChiTietSanPham ctsp = hdct.getChiTietSanPham();
                Integer soLuong = hdct.getSoLuong();
                List<MaDinhDanhCTSP> listMDD = maDinhDanhService.findByHoaDonChiTiet(hdct.getIdHoaDonChiTiet());
                listMDD.forEach(mdd -> {
                    mdd.setTrangThai(TrangThaiMaDinhDanh.LOI);
                    mdd.setNgayBan(null);
                    mdd.setHoaDonChiTiet(null);
                });
                maDinhDanhService.saveMany(listMDD);
            });
            hdctService.saveAll(listHDCT);
        }


//        TaoDonHangRequestGHN donHangRequestGHN = createGHNRequest(donhang);
//        ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(donHangRequestGHN);
//        System.out.println(responseGHN);
        donHangService.updateTrangThaiDonHang(donHang);

        Page<DonHang> donHangs = donHangService.getAll(1);

        model.addAttribute("list", donHangs.getContent());
        return "redirect:/admin/don-hang";
    }


}
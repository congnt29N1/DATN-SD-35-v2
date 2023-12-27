package com.example.datn.controller.restcontroller.donhang;


import com.example.datn.cache.DiaChiCache;
import com.example.datn.configure.VNPayConfig;
import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.MaDinhDanhCTSP;
import com.example.datn.giaohangnhanhservice.DiaChiAPI;
import com.example.datn.giaohangnhanhservice.DonHangAPI;
import com.example.datn.giaohangnhanhservice.request.ChiTietItemRequestGHN;
import com.example.datn.giaohangnhanhservice.request.PhiVanChuyenRequest;
import com.example.datn.request.DonHangRequest;
import com.example.datn.request.HoaDonChiTietRequest;
import com.example.datn.request.ThemDonHangRequest;
import com.example.datn.response.DonHangResponse;
import com.example.datn.response.HoaDonChiTietResponse;
import com.example.datn.response.VNPayUrlResponse;
import com.example.datn.service.*;
import com.example.datn.utils.PhuongThucThanhToan;
import com.example.datn.utils.TrangThaiMaDinhDanh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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

    @Autowired
    GioHangService gioHangService;
    @Autowired
    MaDinhDanhService maDinhDanhService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietGioHangService chiTietGioHangService;
    @PostMapping("/tinh-phi-van-chuyen")
    public ResponseEntity<?> getPhiVanChuyen(@RequestBody PhiVanChuyenRequest phiVanChuyenRequest) {
        try {
            Integer fee = DonHangAPI.getFee(phiVanChuyenRequest);
            return ResponseEntity.status(HttpStatus.OK).body(fee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
//    @PostMapping("/them-don-hang")
//    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
//        try {
//            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
//            DonHang donHang = DonHang.builder()
//                    .khachHang(khachHang)
//                    .ngayTao(new Date())
//                    .trangThaiDonHang(0)
//                    .idTinhThanh(themDonHangRequest.getIdTinhThanh())
//                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
//                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
//                    .diaChi(themDonHangRequest.getDiaChi())
//                    .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
//                    .ghiChu(themDonHangRequest.getGhiChu())
//                    .ngayCapNhap(new Date())
//                    .maDonHang("DH"+System.currentTimeMillis())
//                    .phuongThuc(PhuongThucThanhToan.TRA_SAU)
//                    .tongTien(hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()))
//                    .build();
//            DonHang savedDonHang = donHangService.save(donHang);
//            List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
//            hdctService.saveAll(listHoaDonChiTiet);
//            HashMap<Integer,Integer> hashCTSPIdAndSoLuong = new HashMap();
//            themDonHangRequest.getListHoaDonChiTietRequest().forEach(item->{
//                hashCTSPIdAndSoLuong.put(item.getIdChiTietSanPham(),item.getSoLuong());
//            });
//            chiTietGioHangService.removeByCTSPAndKhachHang(khachHang.getIdKhachHang() , hashCTSPIdAndSoLuong);
//            return ResponseEntity.status(HttpStatus.OK).body(savedDonHang);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
//        }
//    }
@PostMapping("/them-don-hang")
public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
    try {
        // Lấy thông tin khách hàng và tạo đơn hàng
        KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
        DonHang donHang = DonHang.builder()
                .khachHang(khachHang)
                .ngayTao(new Date())
                .trangThaiDonHang(0)
                .idTinhThanh(themDonHangRequest.getIdTinhThanh())
                .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                .diaChi(themDonHangRequest.getDiaChi())
                .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                .ghiChu(themDonHangRequest.getGhiChu())
                .ngayCapNhap(new Date())
                .maDonHang("DH" + System.currentTimeMillis())
                .phuongThuc(PhuongThucThanhToan.TRA_SAU)
                .tongTien(hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()))
                .build();

        // Lưu đơn hàng và lấy đơn hàng đã lưu
        DonHang savedDonHang = donHangService.save(donHang);

        // Lấy danh sách chi tiết hóa đơn từ request và lưu vào CSDL
        List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
        hdctService.saveAll(listHoaDonChiTiet);

        // Kiểm tra số lượng sản phẩm trước khi đặt hàng
        boolean isAvailable = kiemTraSoLuongSanPham(themDonHangRequest.getListHoaDonChiTietRequest());
        if (!isAvailable) {
            // Nếu số lượng sản phẩm đã hết, trả về thông báo lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng sản phẩm đã hết.");
        }

        // Lấy danh sách mã định danh liên quan đến các chi tiết sản phẩm trong đơn hàng
        List<MaDinhDanhCTSP> listMDD = new ArrayList<>();
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            ChiTietSanPham ctsp = hdct.getChiTietSanPham();
            Integer soLuong = hdct.getSoLuong();
            listMDD.addAll(maDinhDanhService.findByChiTietSanPham(ctsp, soLuong));
        }

        // Cập nhật trạng thái của từng mã định danh trong danh sách thành "Đang giao"
        for (MaDinhDanhCTSP mdd : listMDD) {
            mdd.setTrangThai(TrangThaiMaDinhDanh.DANG_GIAO);
        }

        // Lưu lại danh sách mã định danh đã được cập nhật
        maDinhDanhService.saveMany(listMDD);

        // Xóa các sản phẩm đã đặt hàng khỏi giỏ hàng của khách hàng
        HashMap<Integer, Integer> hashCTSPIdAndSoLuong = new HashMap<>();
        themDonHangRequest.getListHoaDonChiTietRequest().forEach(item -> {
            hashCTSPIdAndSoLuong.put(item.getIdChiTietSanPham(), item.getSoLuong());
        });
        chiTietGioHangService.removeByCTSPAndKhachHang(khachHang.getIdKhachHang(), hashCTSPIdAndSoLuong);

        // Trả về thông báo thành công và thông tin đơn hàng đã tạo
        return ResponseEntity.status(HttpStatus.OK).body(savedDonHang);
    } catch (Exception e) {
        // Nếu có lỗi, trả về thông báo lỗi và thông tin lỗi
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}


    // Hàm kiểm tra số lượng sản phẩm
    private boolean kiemTraSoLuongSanPham(List<HoaDonChiTietRequest> listHoaDonChiTietRequest) {
        for (HoaDonChiTietRequest hdctRequest : listHoaDonChiTietRequest) {
            ChiTietSanPham ctsp = chiTietSanPhamService.getChiTietSanPhamById(hdctRequest.getIdChiTietSanPham());
            if (ctsp != null) {
                int soLuongConLai = maDinhDanhService.countMaDinhDanh(ctsp.getIdChiTietSanPham());
                if (soLuongConLai < hdctRequest.getSoLuong()) {
                    // Nếu số lượng mã định danh đã hết, trả về false
                    return false;
                }
            }
        }
        // Nếu không có mã định danh nào hết, trả về true
        return true;
    }

    private HashMap<Integer, String> getListTP() {
        return DiaChiCache.hashMapTinhThanh;
    }

    private HashMap<String, String> getListPX(Integer idTP) throws Exception {
        return DiaChiAPI.callGetPhuongXaAPI(idTP);
    }
    @PostMapping("/thanh-toan-vnpay")
    public ResponseEntity<VNPayUrlResponse> thanhToanVNPAY(@RequestBody ThemDonHangRequest themDonHangRequest) throws IOException {

        KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
        DonHang donHang = DonHang.builder()
                .khachHang(khachHang)
                .ngayTao(new Date())
                .trangThaiDonHang(0)
                .idTinhThanh(themDonHangRequest.getIdTinhThanh())
                .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                .diaChi(themDonHangRequest.getDiaChi())
                .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                .ghiChu(themDonHangRequest.getGhiChu())
                .ngayCapNhap(new Date())
                .maDonHang("DH"+System.currentTimeMillis())
                .tongTien(hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()))
                .phuongThuc(PhuongThucThanhToan.VNPAY)
                .build();
        DonHang savedDonHang = donHangService.save(donHang);
        List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
        hdctService.saveAll(listHoaDonChiTiet);
        HashMap<Integer,Integer> hashCTSPIdAndSoLuong = new HashMap();
        themDonHangRequest.getListHoaDonChiTietRequest().forEach(item->{
            hashCTSPIdAndSoLuong.put(item.getIdChiTietSanPham(),item.getSoLuong());
        });
        chiTietGioHangService.removeByCTSPAndKhachHang(khachHang.getIdKhachHang() , hashCTSPIdAndSoLuong);
        Double amount = (hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest())+ themDonHangRequest.getPhiVanChuyen()) * 100;
        String vnp_Version = VNPayConfig.version;
        String vnp_Command = VNPayConfig.command;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String currCode = VNPayConfig.curr_code;
        String bank_code = VNPayConfig.bank_code;
        String vnp_TxnRef = String.valueOf(savedDonHang.getIdDonHang());
//        String vnp_OrderInfo = "Thanh Toán đơn hàng: "+vnp_TxnRef;
//        String orderType = request.getParameter(VNPayConfig.order_type);
        String location = VNPayConfig.location;
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_ReturnUrl = VNPayConfig.vnp_Returnurl;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.intValue()));
        vnp_Params.put("vnp_CurrCode", currCode);
        vnp_Params.put("vnp_BankCode", bank_code);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", location);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.status(HttpStatus.OK).body(new VNPayUrlResponse(paymentUrl));
    }
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

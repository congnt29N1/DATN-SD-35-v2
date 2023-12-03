package com.example.datn.mapper;

import com.example.datn.cache.DiaChiCache;
import com.example.datn.entity.DonHang;
import com.example.datn.giaohangnhanhservice.DiaChiAPI;
import com.example.datn.response.DonHangResponse;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

@Getter
@Setter
public class DonHangMapping {
    public static DonHangResponse mapEntitytoResponseBT(DonHang donHang) throws Exception {
        DonHangResponse donHangResponse;
        if(donHang.getIdTinhThanh() == null || donHang.getIdQuanHuyen()== null|| donHang.getIdPhuongXa() == null ){
            donHangResponse =  DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(null)
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(null)
                    .QuanHuyen(null)
                    .TinhThanh(null)
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();

        }else {
            donHangResponse = DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(donHang.getDiaChi())
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(getXa(donHang.getIdQuanHuyen(), donHang.getIdPhuongXa()))
                    .QuanHuyen(getQuan(donHang.getIdTinhThanh(), donHang.getIdQuanHuyen()))
                    .TinhThanh(getTinh(donHang.getIdTinhThanh()))
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();
        }
        return donHangResponse;
    }
    public  static DonHangResponse mapEntitytoResponse(DonHang donHang) throws Exception {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        DonHangResponse donHangResponse;
        if(donHang.getDiaChi() == null || donHang.getIdTinhThanh() == null || donHang.getIdQuanHuyen()== null|| donHang.getIdPhuongXa() == null ){
            donHangResponse =  DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(null)
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(null)
                    .QuanHuyen(null)
                    .TinhThanh(null)
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();

        }else {
            donHangResponse = DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(donHang.getDiaChi())
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(getXa(donHang.getIdQuanHuyen(), donHang.getIdPhuongXa()))
                    .QuanHuyen(getQuan(donHang.getIdTinhThanh(), donHang.getIdQuanHuyen()))
                    .TinhThanh(getTinh(donHang.getIdTinhThanh()))
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .ngayCapNhap(df.format(donHang.getNgayCapNhap()))
                    .build();
        }
        return donHangResponse;
    }


    public static String  getTinh(Integer idTP) throws Exception {
        HashMap<Integer,String> listTP = DiaChiCache.hashMapTinhThanh;
        String tinh = listTP.get(idTP);
        return tinh;
    }
    public static String  getQuan(Integer idTP , Integer idQH) throws Exception {
        HashMap<Integer,String> listQH  = DiaChiAPI.callGetQuanHuyenAPI(idTP);
        String quan = listQH.get(idQH);
        return quan;
    }
    public static String  getXa( Integer idQH, String idPX) throws Exception {
        HashMap<String, String> listPX  = DiaChiAPI.callGetPhuongXaAPI(idQH);
        String xa = listPX.get(idPX);
        return xa;
    }
}

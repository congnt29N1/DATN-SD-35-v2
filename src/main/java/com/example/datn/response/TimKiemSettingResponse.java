package com.example.datn.response;

import com.example.datn.entity.CauTrucKhuy;
import com.example.datn.entity.ChatLieu;
import com.example.datn.entity.DanhMuc;
import com.example.datn.entity.HoaTiet;
import com.example.datn.entity.KichCo;
import com.example.datn.entity.KieuDet;
import com.example.datn.entity.KieuTui;
import com.example.datn.entity.LopLot;
import com.example.datn.entity.MauSac;
import com.example.datn.entity.VeAo;
import com.example.datn.entity.XeTa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class TimKiemSettingResponse {
    private List<DanhMuc> listDanhMuc;
    private List<KichCo> listKichCo;
    private List<MauSac> listMauSac;
    private List<ChatLieu> listChatLieu;
    private List<CauTrucKhuy> listCauTrucKhuy;
    private List<HoaTiet> listHoaTiet;
    private List<KieuDet> listKieuDet;
    private List<KieuTui> listKieuTui;
    private List<LopLot> listLopLot;
    private List<VeAo> listVeAo;
    private List<XeTa> listXeTa;
}

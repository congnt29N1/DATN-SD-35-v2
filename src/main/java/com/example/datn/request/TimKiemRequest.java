package com.example.datn.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TimKiemRequest {
    @JsonProperty("tenSanPham")
    private String tenSanPham;
    @JsonProperty("danhMucId")
    private List<Integer> danhMucId;
    @JsonProperty("mauSacId")
    private List<Integer> mauSacId;
    @JsonProperty("chatLieuId")
    private List<Integer> chatLieuId;
    @JsonProperty("kichCoId")
    private List<Integer> kichCoId;
    @JsonProperty("cauTrucKhuyId")
    private List<Integer> cauTrucKhuyId;
    @JsonProperty("hoaTietId")
    private List<Integer> hoaTietId;
    @JsonProperty("kieuTuiId")
    private List<Integer> kieuTuiId;
    @JsonProperty("kieuDetId")
    private List<Integer> kieuDetId;
    @JsonProperty("lopLotId")
    private List<Integer> lopLotId;
    @JsonProperty("veAoId")
    private List<Integer> veAoId;
    @JsonProperty("xeTaId")
    private List<Integer> xeTaId;
}

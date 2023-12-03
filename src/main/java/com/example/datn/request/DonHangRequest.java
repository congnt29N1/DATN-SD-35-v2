package com.example.datn.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangRequest {
    private Integer idDonHang;
    private String lyDo;
    private Integer trangThaiDonHang;
}

package com.example.datn.response;

import com.example.datn.entity.AnhSanPham;
import com.example.datn.entity.ChiTietSanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TimKiemResponse {
    private Integer sanPhamID;
    private String tenSanPham;
    private Double giaSanPham;
    private List<AnhSanPham> listAnhSanPham;
    private List<ChiTietSanPham> listChiTietSanPham ;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimKiemResponse that = (TimKiemResponse) o;
        return Objects.equals(sanPhamID, that.sanPhamID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sanPhamID);
    }
}

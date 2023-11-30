package com.example.datn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sanpham")
public class SanPham {
    public static final int HOAT_DONG = 1;
    public static final int KHONG_HOAT_DONG = 0;
    @Id
    @Column(name = "id_san_pham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSanPham;

    @JoinColumn(name = "ma_san_pham")
    private String maSanPham;

    @ManyToOne
    @JoinColumn(name = "id_danh_muc")
    private DanhMuc danhMuc;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "mo_ta_san_pham")
    private String moTaSanPham;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( fetch = FetchType.EAGER,mappedBy = "sanPham", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ChiTietSanPham> listChiTietSanPham ;


    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<AnhSanPham> listAnhSanPham;


    @Column(name = "main_image", nullable = false)
    private String mainImage;


    public void addExtraImage(String imageName){
        this.listAnhSanPham.add(new AnhSanPham(imageName,this));
    }

    @Transient
    public String getMainImagePath(){
        if (idSanPham == null || mainImage == null) return "/assets/images/image-thumbnail.png";
        return "/assets/images/"+ this.listAnhSanPham.get(0).getLink();
    }

    @Transient
    private String currentMainImage; // Trường ẩn để lưu tên ảnh hiện tại

    public SanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
}

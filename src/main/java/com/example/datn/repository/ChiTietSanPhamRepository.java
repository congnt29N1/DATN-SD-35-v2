package com.example.datn.repository;

import com.example.datn.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Integer> {
    @Query("""
        SELECT c 
        FROM ChiTietSanPham c
        WHERE LOWER(CONCAT(c.maChiTietSanPham,c.sanPham.tenSanPham)) LIKE %?1%  AND c.sanPham.trangThai = 1
    """)
    public Page<ChiTietSanPham> searchByKey(String key, Pageable pageable);

    @Query(nativeQuery = true,value = """
        SELECT *
        FROM chitietsanpham c JOIN sanpham sp ON c.id_san_pham = sp.id_san_pham
        WHERE sp.ma_san_pham = ?1
    """)
    public Page<ChiTietSanPham> findByMaSP(String maSanPham, Pageable pageable);
    @Query("""
        SELECT ctsp
        FROM ChiTietSanPham ctsp
        WHERE ctsp.sanPham.trangThai = 1
    """)
    public Page<ChiTietSanPham> findAllHung(Pageable pageable);

    ChiTietSanPham findByMaChiTietSanPham(String ma);
    ChiTietSanPham findByIdChiTietSanPham(Integer idChiTietSanPham);
    public Page<ChiTietSanPham> findBySanPham_TenSanPhamContainingIgnoreCase(String tenSanPham, Pageable pageable);
    @Query("""
        SELECT ctsp
        FROM ChiTietSanPham ctsp
        WHERE (LOWER(CONCAT(ctsp.maChiTietSanPham, ctsp.sanPham.tenSanPham)) LIKE %?1%)
        AND (ctsp.sanPham.tenSanPham LIKE %?2%)
    """)
    public Page<ChiTietSanPham> findByProductNameAndKeyword(String keyword, String productName, Pageable pageable);

    @Query(value = "select ctsp from ChiTietSanPham ctsp where ctsp.sanPham.idSanPham = ?1")
    List<ChiTietSanPham> findByIdSp(Integer IdSP);

    @Query("""
        SELECT ctsp
        FROM ChiTietSanPham ctsp
        WHERE UPPER(ctsp.maChiTietSanPham) LIKE %?1%
            OR UPPER(ctsp.kichCo.tenKichCo) LIKE %?1%
            OR UPPER(ctsp.khuyenMai.tenKhuyenMai) LIKE %?1%
            OR UPPER(ctsp.mauSac.tenMauSac) LIKE %?1%
            OR UPPER(ctsp.sanPham.tenSanPham) LIKE %?1%
            OR UPPER(ctsp.xeTa.tenXeTa) LIKE %?1%
            OR UPPER(ctsp.kieuDet.tenKieuDet) LIKE %?1%
            OR UPPER(ctsp.kieuTui.tenKieuTui) LIKE %?1%
            OR UPPER(ctsp.veAo.tenVeAo) LIKE %?1%
            OR UPPER(ctsp.hoaTiet.tenHoaTiet) LIKE %?1%
            OR UPPER(ctsp.cauTrucKhuy.tenCauTrucKhuy) LIKE %?1%
            OR UPPER(ctsp.lopLot.tenLopLot) LIKE %?1%
            OR UPPER(ctsp.chatLieu.tenChatLieu) LIKE %?1%
    """)
    public Page<ChiTietSanPham> findByKeyword(String keyword, Pageable pageable);
    @Query("SELECT ctsp FROM ChiTietSanPham  ctsp WHERE UPPER(CONCAT(ctsp.idChiTietSanPham,' ', ctsp.maChiTietSanPham," +
            " ' ',ctsp.kichCo,' ', ctsp.khuyenMai,' ', ctsp.xeTa, ctsp.kieuTui,' ', ctsp.kieuDet,' ', ctsp.veAo,' '," +
            " ctsp.hoaTiet,' ', ctsp.chatLieu,' ', ctsp.cauTrucKhuy,' ', ctsp.lopLot,' ', ctsp.chatLieu,' ',' '," +
            " ctsp.mauSac,' ',ctsp.sanPham)) LIKE %?1%")
    public Page<ChiTietSanPham> findAll(String keyword,Pageable pageable);
    ChiTietSanPham findBySanPham_TenSanPhamAndMauSac_TenMauSacAndChatLieu_TenChatLieuAndKichCo_TenKichCoAndCauTrucKhuy_TenCauTrucKhuyAndHoaTiet_TenHoaTietAndKieuTui_TenKieuTuiAndKieuDet_TenKieuDetAndLopLot_TenLopLotAndVeAo_TenVeAoAndXeTa_TenXeTa(
            String tenSanPham,
            String tenMauSac,
            String tenChatLieu,
            String tenKichCo,
            String tenCauTrucKhuy,
            String tenHoaTiet,
            String tenKieuTui,
            String tenKieuDet,
            String tenLopLot,
            String tenVeAo,
            String tenXeTa
    );


}

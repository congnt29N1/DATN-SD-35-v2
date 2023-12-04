package com.example.datn.repository;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.MaDinhDanhCTSP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MaDinhDanhRepository extends JpaRepository<MaDinhDanhCTSP,Integer> {

    @Query("select m from MaDinhDanhCTSP m where m.maDinhDanh like  %:keyword%")
    Page<MaDinhDanhCTSP> findByIdImeiLike(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select count(s) from MaDinhDanhCTSP s where s.trangThai = 1 and s.chiTietSanPham.idChiTietSanPham = ?1")
    Integer countMaDinhDanh (Integer chiTietSanPham);
    @Query("""
        SELECT COUNT(sr.id)
        FROM MaDinhDanhCTSP sr
        WHERE   sr.chiTietSanPham.idChiTietSanPham = ?1
                AND sr.trangThai = 1
                AND sr.hoaDonChiTiet is null
    """)
    int countByIdCTSPEnabled (int idCTSP);

    List<MaDinhDanhCTSP> findByChiTietSanPhamAndTrangThai(ChiTietSanPham chiTietSanPham, Integer trangThai, Pageable pageable);

    @Query(nativeQuery = true, value = """
       UPDATE ma_dinh_danh_ctsp s1
       SET s1.trang_thai = 3,
           s1.id_hoa_don_chi_tiet = :idHoaDonChiTiet,
           s1.ngay_ban = CURRENT_TIMESTAMP()
       WHERE s1.id IN :listMaDinhDanh
    """)
    @Modifying
    @Transactional
    void themSoLuongAdmin(
            @Param("idHoaDonChiTiet") int idHoaDonChiTiet,
            @Param("listMaDinhDanh") List<Integer> listMaDinhDanh
    );

    @Query(nativeQuery = true, value = """
       UPDATE ma_dinh_danh_ctsp s1
       SET s1.trang_thai = 1,
           s1.id_hoa_don_chi_tiet = null,
           s1.ngay_ban = null
       WHERE s1.id_hoa_don_chi_tiet = :idHoaDonChiTiet
       LIMIT :soLuong
    """)
    @Modifying
    @Transactional
    void giamSoLuongAdmin(
            @Param("idHoaDonChiTiet") int idHoaDonChiTiet,
            @Param("soLuong") int soLuong
    );

    @Query(nativeQuery = true, value = """

                SELECT s.id s
                FROM ma_dinh_danh_ctsp s
                WHERE s.trang_thai = 1
                AND   s.id_chi_tiet_san_pham = :idChiTietSanPham
                LIMIT :soLuong
    """)
    @Modifying
    @Transactional
    List<Integer> getListMaDinhDanh(
            @Param("soLuong") int soLuong,
            @Param("idChiTietSanPham") int idChiTietSanPham
    );

    @Query(nativeQuery = true,value = """
        SELECT COUNT(*) AS count
        FROM ma_dinh_danh_ctsp s
        WHERE s.id_hoa_don_chi_tiet = :idHDCT
    """)
    int soLuongDaMuaByHDCT(@Param("idHDCT") int idHDCT);

    @Query(nativeQuery = true,value = """
        SELECT COUNT(*) AS count
        FROM ma_dinh_danh_ctsp s
        WHERE s.id_chi_tiet_san_pham = :idCTSP
        AND   s.id_hoa_don_chi_tiet IS NULL
    """)
    int soLuongTonByHDCT(@Param("idCTSP") int idCTSP);

    @Modifying
    @Transactional
    @Query("""
        UPDATE MaDinhDanhCTSP s
        SET s.hoaDonChiTiet = null,
            s.ngayBan = null,
            s.trangThai = 1
        WHERE s.hoaDonChiTiet.idHoaDonChiTiet = ?1
    """)
    void xoaSoLuongSanPham(int idHDCT);

    @Query(value = "select s from MaDinhDanhCTSP s where s.hoaDonChiTiet.idHoaDonChiTiet = ?1")
    List<MaDinhDanhCTSP> findByHoaDonChiTiet (Integer idhoaDonChiTiet);
}

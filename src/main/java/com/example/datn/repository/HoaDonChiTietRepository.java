package com.example.datn.repository;

import com.example.datn.entity.DonHang;
import com.example.datn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query(value = """
                SELECT h
                FROM HoaDonChiTiet h JOIN DonHang dh ON h.donHang.idDonHang = dh.idDonHang
                WHERE h.donHang.idDonHang = ?1
                ORDER BY h.idHoaDonChiTiet ASC
            """)
    public List<HoaDonChiTiet> findHDCTBYIdDonHang(int id);

    List<HoaDonChiTiet> findByDonHang(DonHang donHang);

    @Query(value = """
        SELECT h 
        FROM HoaDonChiTiet h JOIN DonHang d ON h.donHang.idDonHang = d.idDonHang
        WHERE d.maDonHang = ?1
    """)
    public List<HoaDonChiTiet> findByMaDonHang(String maDonHang);

    @Query(value = """
        UPDATE HoaDonChiTiet h
        SET h.soLuong = h.soLuong + ?1
        WHERE h.idHoaDonChiTiet = ?2
    """)
    @Modifying
    public void updateSoLuongSanPham(int soLuong, int idHoaDonChiTiet);

    @Query(value = """
        DELETE 
        FROM hoadonchitiet h
        WHERE h.id_hoa_don_chi_tiet = :id
    """, nativeQuery = true)
    @Modifying
    public void xoaHDCT(@Param("id") int idHoaDonChiTiet);

    void deleteByIdHoaDonChiTiet(int id);

    public HoaDonChiTiet findByIdHoaDonChiTiet(int id);

    @Query(value = """
        UPDATE HoaDonChiTiet h
        SET h.soLuong = ?1
        WHERE h.idHoaDonChiTiet = ?2
    """)
    @Modifying
    public void updateSoLuongSanPhamWithEdit(int soLuong, int idHoaDonChiTiet);

    @Query("SELECT NEW  com.example.datn.entity.HoaDonChiTiet(d.chiTietSanPham.sanPham.danhMuc.ten, d.soLuong," +
            "d.giaBan, d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3")
    public List<HoaDonChiTiet> findWithCategoryAndTimeBetween(Date startTime, Date endTime, Integer status);

    @Query("SELECT NEW  com.example.datn.entity.HoaDonChiTiet(d.soLuong, d.chiTietSanPham.sanPham.tenSanPham," +
            "d.giaBan, d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3")
    public List<HoaDonChiTiet> findWithProductAndTimeBetween(Date startTime, Date endTime, Integer status);

    @Query("SELECT NEW  com.example.datn.entity.HoaDonChiTiet(d.chiTietSanPham.idChiTietSanPham, d.soLuong, d.giaBan, " +
            "d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3"
    )
    public List<HoaDonChiTiet> findWithOrderDetailAndTimeBetween(Date startTime, Date endTime, Integer status);
    @Modifying
    @Transactional
    public void deleteByDonHang(DonHang donHang);

    @Query("SELECT count(hd) FROM HoaDonChiTiet hd WHERE hd.donHang.idDonHang = ?1")
    Integer countHD(Integer id);
}

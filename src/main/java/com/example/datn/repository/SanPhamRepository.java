package com.example.datn.repository;

import com.example.datn.entity.ChiTietSanPham;
import com.example.datn.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query(value = "SELECT  * FROM sanpham where sanpham.trang_thai = 1 ORDER BY sanpham.id_san_pham DESC LIMIT 8", nativeQuery = true)
    List<SanPham> getSPnew();

    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h where h.donHang.trangThaiDonHang = 3 group by h.chiTietSanPham.sanPham   ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();
    @Query(value = "SELECT s.listChiTietSanPham  FROM SanPham s where s.idSanPham = ?1 and s.trangThai = 1 ")
    List<ChiTietSanPham> getCTSP(Integer idSanPham);
    @Query("SELECT sp FROM SanPham  sp WHERE UPPER(CONCAT(sp.idSanPham,' ',sp.maSanPham,' ', sp.tenSanPham, ' ', sp.danhMuc, ' ',sp.moTaSanPham)) LIKE %?1%")
    Page<SanPham> findAll(String keyword, Pageable pageable);
    SanPham findByTenSanPham(String tenSanPham);
    SanPham findByMaSanPham(String ma);
}

package com.example.datn.repository;



import com.example.datn.entity.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai,Integer> {

    @Query("SELECT km FROM KhuyenMai km WHERE UPPER(CONCAT(km.idKhuyenMai,' ', km.maKhuyenMai,' ', km.tenKhuyenMai,' ',km.moTaKhuyenMai,' ', km.ngayBatDau,' ', km.ngayKetThuc,' ',km.enabled,' ',km.chietKhau))LIKE %?1%")
    public Page<KhuyenMai> findAll(String keyword, Pageable pageable);

    public KhuyenMai findByTenKhuyenMai(String ten);

    KhuyenMai findByMaKhuyenMai(String ma);
    List<KhuyenMai> findByNgayKetThucLessThanAndEnabled(Date today, boolean b);
}

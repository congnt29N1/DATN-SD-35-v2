package com.example.datn.repository;

import com.example.datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Integer> {
    @Query("select gh from GioHang gh where gh.khachHang.idKhachHang = ?1")
    GioHang findGioHang(Integer idKhachhang);

}


package com.example.datn.repository;

import com.example.datn.entity.KieuDet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuDetRepository extends JpaRepository<KieuDet,Integer> {
    @Query("UPDATE KieuDet kd SET kd.enabled = ?2 WHERE kd.idKieuDet = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT kd FROM KieuDet kd WHERE UPPER(CONCAT(kd.idKieuDet, ' ', kd.tenKieuDet, ' ', kd.moTaKieuDet)) LIKE %?1%")
    public Page<KieuDet> findAll(String keyword, Pageable pageable);

    public KieuDet findByTenKieuDet(String ten);
}

package com.example.datn.repository;

import com.example.datn.entity.KieuTui;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuTuiRepository extends JpaRepository<KieuTui,Integer> {
    @Query("UPDATE KieuTui kt SET kt.enabled = ?2 WHERE kt.idKieuTui = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT kt FROM KieuTui kt WHERE UPPER(CONCAT(kt.idKieuTui, ' ', kt.tenKieuTui, ' ', kt.moTaKieuTui)) LIKE %?1%")
    public Page<KieuTui> findAll(String keyword, Pageable pageable);

    public KieuTui findByTenKieuTui(String ten);
}

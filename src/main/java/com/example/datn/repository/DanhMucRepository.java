package com.example.datn.repository;

import com.example.datn.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    @Query("UPDATE DanhMuc dm SET dm.enabled = ?2 WHERE dm.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT dm FROM DanhMuc dm WHERE UPPER(CONCAT(dm.id, ' ', dm.ten)) LIKE %?1%")
    Page<DanhMuc> findAll(String keyword, Pageable pageable);

    DanhMuc findByTen(String ten);
}

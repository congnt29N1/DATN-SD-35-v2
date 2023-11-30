package com.example.datn.repository;


import com.example.datn.entity.CauTrucKhuy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CauTrucKhuyRepository extends JpaRepository<CauTrucKhuy,Integer> {
    @Query("UPDATE CauTrucKhuy ctk SET ctk.enabled = ?2 WHERE ctk.idCauTrucKhuy = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT ctk FROM CauTrucKhuy ctk WHERE UPPER(CONCAT(ctk.idCauTrucKhuy, ' ', ctk.tenCauTrucKhuy, ' ', ctk.moTaCauTrucKhuy)) LIKE %?1%")
    public Page<CauTrucKhuy> findAll(String keyword, Pageable pageable);

    public CauTrucKhuy findByTenCauTrucKhuy(String ten);
}

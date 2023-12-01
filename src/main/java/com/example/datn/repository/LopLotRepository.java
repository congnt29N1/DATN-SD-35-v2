package com.example.datn.repository;

import com.example.datn.entity.LopLot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LopLotRepository extends JpaRepository<LopLot,Integer> {
    @Query("UPDATE LopLot ll SET ll.enabled = ?2 WHERE ll.idLopLot = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT ll FROM LopLot ll WHERE UPPER(CONCAT(ll.idLopLot, ' ', ll.tenLopLot, ' ', ll.moTaLopLot)) LIKE %?1%")
    public Page<LopLot> findAll(String keyword, Pageable pageable);

    public LopLot findByTenLopLot(String ten);
}

package com.example.datn.repository;

import com.example.datn.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    @Query(value = "SELECT ms FROM MauSac ms WHERE UPPER(CONCAT(ms.idMauSac, ' ', ms.tenMauSac)) LIKE %?1%")
    public Page<MauSac> findAll(String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "UPDATE mausac ms SET ms.enabled = ?2 WHERE ms.id_mau_sac = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}

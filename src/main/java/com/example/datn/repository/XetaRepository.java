package com.example.datn.repository;

import com.example.datn.entity.XeTa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface XetaRepository extends JpaRepository<XeTa,Integer> {
    @Query(value = "SELECT xt FROM XeTa xt WHERE UPPER(CONCAT(xt.idXeTa, ' ', xt.tenXeTa)) LIKE %?1%")
    public Page<XeTa> findAll(String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "UPDATE xeta xt SET xt.enabled = ?2 WHERE xt.id_xe_ta = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

}

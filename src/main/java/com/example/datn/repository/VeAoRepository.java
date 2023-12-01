package com.example.datn.repository;


import com.example.datn.entity.VeAo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VeAoRepository extends JpaRepository<VeAo,Integer> {
    @Query("UPDATE VeAo va SET va.enabled = ?2 WHERE va.idVeAo = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT va FROM VeAo va WHERE UPPER(CONCAT(va.idVeAo, ' ', va.tenVeAo, ' ', va.moTaVeAo)) LIKE %?1%")
    public Page<VeAo> findAll(String keyword, Pageable pageable);

    public VeAo findByTenVeAo(String ten);
}

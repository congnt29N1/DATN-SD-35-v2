package com.example.datn.repository;


import com.example.datn.entity.HoaTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaTietRespository extends JpaRepository<HoaTiet, Integer> {
    @Query("UPDATE HoaTiet ht SET ht.enabled = ?2 WHERE ht.idHoaTiet = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT ht FROM HoaTiet ht WHERE UPPER(CONCAT(ht.idHoaTiet, ' ', ht.tenHoaTiet, ' ', ht.moTaHoaTiet)) LIKE %?1%")
    public Page<HoaTiet> findAll(String keyword, Pageable pageable);

    public HoaTiet findByTenHoaTiet(String ten);

}

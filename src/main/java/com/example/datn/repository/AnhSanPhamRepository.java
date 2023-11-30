package com.example.datn.repository;

import com.example.datn.entity.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhSanPhamRepository  extends JpaRepository<AnhSanPham,Integer> {
}

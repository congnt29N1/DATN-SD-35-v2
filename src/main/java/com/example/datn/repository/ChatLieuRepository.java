package com.example.datn.repository;


import com.example.datn.entity.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu,Integer> {
    @Query("UPDATE ChatLieu cl SET cl.enabled = ?2 WHERE cl.idChatLieu = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT cl FROM ChatLieu cl WHERE UPPER(CONCAT(cl.idChatLieu, ' ', cl.tenChatLieu, ' ', cl.moTaChatLieu)) LIKE %?1%")
    public Page<ChatLieu> findAll(String keyword, Pageable pageable);

    public ChatLieu findByTenChatLieu(String ten);
}

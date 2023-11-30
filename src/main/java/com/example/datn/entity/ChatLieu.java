package com.example.datn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//done
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chatlieu")
public class ChatLieu {
    @Id
    @Column(name = "id_chat_lieu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChatLieu;

    @Column(name = "ten_chat_lieu")
    private String tenChatLieu;

    @Column(name = "mo_ta_chat_lieu")
    private String moTaChatLieu;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}

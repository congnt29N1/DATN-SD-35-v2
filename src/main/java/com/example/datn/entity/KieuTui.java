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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "kieutui")
public class KieuTui {
    @Id
    @Column(name = "id_kieu_tui")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKieuTui;

    @Column(name = "ten_kieu_tui")
    private String tenKieuTui;

    @Column(name = "mo_ta_kieu_tui")
    private String moTaKieuTui;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}

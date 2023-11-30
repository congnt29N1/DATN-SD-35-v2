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
@Table(name = "hoatiet")
public class HoaTiet {
    @Id
    @Column(name = "id_hoa_tiet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHoaTiet;

    @Column(name = "ten_hoa_tiet")
    private String tenHoaTiet;

    @Column(name = "mo_ta_hoa_tiet")
    private String moTaHoaTiet;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}

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
@Table(name = "loplot")
public class LopLot {
    @Id
    @Column(name = "id_lop_lot")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLopLot;

    @Column(name = "ten_lop_lot")
    private String tenLopLot;

    @Column(name = "mo_ta_lop_lot")
    private String moTaLopLot;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}

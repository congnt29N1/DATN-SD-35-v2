package com.example.datn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "veao")
public class VeAo {
    @Id
    @Column(name = "id_ve_ao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVeAo;

    @Column(name = "ten_ve_ao")
    private String tenVeAo;

    @Column(name = "mo_ta_ve_ao")
    private String moTaVeAo;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}

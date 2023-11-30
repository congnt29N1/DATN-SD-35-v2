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
@Table(name = "xeta")
public class XeTa {
    @Id
    @Column(name = "id_xe_ta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idXeTa;

    @Column(name = "ten_xe_ta")
    private String tenXeTa;

    @Column(name = "mo_ta_xe_ta")
    private String moTaXeTa;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
    //xeta
}

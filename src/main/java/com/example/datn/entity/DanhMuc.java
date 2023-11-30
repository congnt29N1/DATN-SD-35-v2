package com.example.datn.entity;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten",length = 128, nullable = false, unique = true)
    private String ten;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    public DanhMuc() {
    }

    public DanhMuc(Integer id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public DanhMuc(Integer id, String ten, boolean enabled) {
        this.id = id;
        this.ten = ten;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DanhMuc(String ten) {
        this.ten = ten;
    }
}

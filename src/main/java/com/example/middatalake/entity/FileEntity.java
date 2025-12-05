package com.example.middatalake.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "t-file")
public class FileEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;

}

package com.example.middatalake.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t-file")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String bucketName;

    @Column(nullable = false)
    private String objectName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate;

}

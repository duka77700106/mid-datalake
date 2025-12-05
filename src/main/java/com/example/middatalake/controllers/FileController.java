package com.example.middatalake.controllers;


import com.example.middatalake.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {
    private final FileRepository fileRepository;
}

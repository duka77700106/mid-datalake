package com.example.middatalake.controllers;


import com.example.middatalake.services.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно загружен: " + file.getOriginalFilename());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}

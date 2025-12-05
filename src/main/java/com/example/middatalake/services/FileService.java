package com.example.middatalake.services;


import com.example.middatalake.entity.FileEntity;
import com.example.middatalake.repository.FileRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot + 1).toLowerCase() : "";
    }

    private String getContentType(String extension) {
        return switch (extension) {
            case "txt" -> "text/plain";
            case "png" -> "image/png";
            case "json" -> "application/json";
            default -> "application/octet-stream";
        };
    }

    public void uploadFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        String fileType = getFileExtension(filename);
        if (!fileType.matches("txt|png|json")) {
            throw new IllegalArgumentException(
                    String.format("Неподдерживаемый тип файла: '%s'. Разрешены только: txt, png, json", fileType)
            );
        }


        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(stream, file.getSize(), -1)
                            .contentType(getContentType(fileType))
                            .build()
            );
            log.info("File '{}' uploaded to MinIO bucket '{}'", filename, bucketName);
        } catch (MinioException e) {
            log.error("MinIO error: {}", e.getMessage(), e);
            throw new Exception("Ошибка при загрузке файла в MinIO: " + e.getMessage(), e);
        }

        FileEntity fileEntity = FileEntity.builder()
                .fileName(filename)
                .originalName(filename)
                .fileSize(file.getSize())
                .fileType(fileType)
                .contentType(getContentType(fileType))
                .bucketName(bucketName)
                .objectName(filename)
                .uploadDate(LocalDateTime.now())
                .build();

        fileRepository.save(fileEntity);
        log.info("Файл '{}' сақталды", filename);
    }
}


package com.example.middatalake.services;


import com.example.middatalake.repository.FileRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final MinioClient minioClient;
    private String bucketName;

    public void uploadFile(MultipartFile file) throws Exception {
        try (var stream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(stream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

    }
}

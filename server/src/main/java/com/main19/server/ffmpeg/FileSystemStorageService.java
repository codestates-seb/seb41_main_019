package com.main19.server.ffmpeg;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileSystemStorageService {
    private final FFmpegService fFmpegService;
    private final Path rootLocation = Paths.get("/home/ubuntu/main19/ffmpeg");
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new BusinessLogicException(
                        ExceptionCode.MEDIA_NOT_FOUND);
            }
            try (InputStream inputStream = file.getInputStream()) {
                log.info("# store coffee image!");
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            fFmpegService.export(file);
        }
        catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
        }
    }
}

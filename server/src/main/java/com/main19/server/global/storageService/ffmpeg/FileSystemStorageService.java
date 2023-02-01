package com.main19.server.global.storageService.ffmpeg;

import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileSystemStorageService {
    private final FFmpegService fFmpegService;
    private final Path rootLocation = Paths.get("/home/ubuntu/main19/ffmpeg");
    public List<File> store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new BusinessLogicException(
                        ExceptionCode.MEDIA_NOT_FOUND);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            List<File> returnFiles = new ArrayList<>();
            returnFiles.add(new File(fFmpegService.export(file)));
            returnFiles.add(new File(fFmpegService.exportThumbnail(file)));
            return returnFiles;
        }
        catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
        }
    }

    @Scheduled(cron = "0  0  3  *  *  *")
    public void delete() {
        File folder = new File("/home/ubuntu/main19/ffmpeg");
        try {
                File[] listFiles = folder.listFiles();

                for (File file : listFiles) {
                    file.delete();
                }

                log.info("Media files deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

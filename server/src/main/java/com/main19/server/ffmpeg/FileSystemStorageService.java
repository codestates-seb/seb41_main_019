package com.main19.server.ffmpeg;

import com.amazonaws.util.IOUtils;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileSystemStorageService {
    private final FFmpegService fFmpegService;
    private final Path rootLocation = Paths.get("C:/Users/hyein/Desktop/image");
    public File store(MultipartFile file) {
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
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            File returnFile = new File(fFmpegService.export(file));
            return returnFile;
        }
        catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
        }
    }
}

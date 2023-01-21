package com.main19.server.storageService.ffmpeg;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // todo 스케쥴러 사용하여 시스템 폴더 내 파일 삭제, 혹시 저장로직이랑 동시에 실행되면 에러나기에 요청을 다시 해야하는 단점이 있따... 이거 어뜨케 하면 조으까 ㅇㅅㅇ??
    // todo 10분에 한번 씩으로 설정했는데 이건 추후에 논의하여 변경
    @Scheduled(fixedRate = 600000)
    public void delete() {
        File folder = new File("C:/Users/hyein/Desktop/image");
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

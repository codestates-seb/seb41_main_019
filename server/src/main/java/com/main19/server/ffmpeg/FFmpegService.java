package com.main19.server.ffmpeg;

import com.amazonaws.services.s3.AmazonS3;
import com.main19.server.s3service.S3StorageService;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FFmpegService {
    public void export(MultipartFile file) throws IOException {

        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");

        String path = "/home/ubuntu/main19/ffmpeg/" + file.getOriginalFilename();
        System.out.println(path);
        FFmpegBuilder builder = new FFmpegBuilder().setInput(path) // 파일경로
                .overrideOutputFiles(true) // 오버라이드
                .addOutput("/home/ubuntu/main19/ffmpeg/" + file.getOriginalFilename()) // 저장 경로 ( mov to mp4 )
                .setFormat("avi") // 포맷 ( 확장자 )
                .setVideoCodec("libx264") // 비디오 코덱
                .disableSubtitle() // 서브타이틀 제거
                .setAudioChannels(2) // 오디오 채널 ( 1 : 모노 , 2 : 스테레오 )
                .setVideoResolution(600, 600) // 동영상 해상도
                .setVideoBitRate(1464800) // 비디오 비트레이트
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // ffmpeg 빌더 실행 허용
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }
}

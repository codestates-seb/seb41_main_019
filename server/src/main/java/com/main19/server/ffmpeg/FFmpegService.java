package com.main19.server.ffmpeg;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FFmpegService {
    public String export(MultipartFile file) throws IOException {

        FFmpeg ffmpeg = new FFmpeg("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffprobe");

        String filename = file.getOriginalFilename();

        String path = "C:/Users/hyein/Desktop/image/" + filename;
        System.out.println(path);
        FFmpegBuilder builder = new FFmpegBuilder().setInput(path) // 파일경로
                .overrideOutputFiles(true) // 오버라이드
                .addOutput("C:/Users/hyein/Desktop/image/" + filename.substring(0, filename.lastIndexOf(".")) + "converted.mp4") // 저장 경로 ( mov to mp4 )
                .setFormat("mp4") // 포맷 ( 확장자 )
                .setVideoCodec("libx264") // 비디오 코덱
                .disableSubtitle() // 서브타이틀 제거
                .setAudioChannels(2) // 오디오 채널 ( 1 : 모노 , 2 : 스테레오 )
                .setVideoResolution(720, 720) // 동영상 해상도
                .setVideoBitRate(1464800) // 비디오 비트레이트
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // ffmpeg 빌더 실행 허용
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        return "C:/Users/hyein/Desktop/image/" + filename.substring(0, filename.lastIndexOf(".")) + "converted.mp4";
    }
}

package com.main19.server.storageService.ffmpeg;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FFmpegService {
    public String export(MultipartFile file) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffmpeg"); // todo 서버 배포 시 "/usr/bin/ffmpeg"로 변경
        FFprobe ffprobe = new FFprobe("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffprobe"); // todo 서버 배포 시 "/usr/bin/ffprobe"로 변경

        String filename = file.getOriginalFilename();
        String path = "C:/Users/hyein/Desktop/image/" + filename; // todo 서버 배포 시 "/home/ubuntu/main19/ffmpeg/" 로 변경

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
        return "C:/Users/hyein/Desktop/image/" + filename.substring(0, filename.lastIndexOf(".")) + "converted.mp4"; // todo 서버 배포 시 "/home/ubuntu/main19/ffmpeg/" 로 변경
    }

    public String exportThumbnail(MultipartFile file) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffmpeg"); // todo 서버 배포 시 "/usr/bin/ffmpeg"로 변경
        FFprobe ffprobe = new FFprobe("C:/Users/hyein/Desktop/ffmpeg-5.1.2-essentials_build/ffmpeg-5.1.2-essentials_build/bin/ffprobe"); // todo 서버 배포 시 "/usr/bin/ffprobe"로 변경

        String filename = file.getOriginalFilename();
        String path = "C:/Users/hyein/Desktop/image/" + filename; // todo 서버 배포 시 "/home/ubuntu/main19/ffmpeg/" 로 변경

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)					// output 파일을 덮어쓸 것인지 여부(false일 경우, output path에 해당 파일이 존재할 경우 예외 발생 - File 'C:/Users/Desktop/test.png' already exists. Exiting.)
                .setInput(path)     					// 썸네일 이미지 추출에 사용할 영상 파일의 절대 경로
                .addExtraArgs("-ss", "00:00:01") 			// 영상에서 추출하고자 하는 시간 - 00:00:01은 1초를 의미
                .addOutput("C:/Users/hyein/Desktop/image/" + filename.substring(0, filename.lastIndexOf(".")) + ".gif") 		// 저장 절대 경로(확장자 미 지정 시 예외 발생 - [NULL @ 000002cc1f9fa500] Unable to find a suitable output format for 'C:/Users/Desktop/test')
                .setFrames(1)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);		// FFmpeg 명령어 실행을 위한 FFmpegExecutor 객체 생성
        executor.createJob(builder).run();									// one-pass encode

        return "C:/Users/hyein/Desktop/image/" + filename.substring(0, filename.lastIndexOf(".")) + ".gif";
    }
}

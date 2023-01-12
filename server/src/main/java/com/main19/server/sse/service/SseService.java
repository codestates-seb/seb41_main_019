package com.main19.server.sse.service;

import com.main19.server.sse.entity.Sse;
import com.main19.server.sse.entity.Sse.SseType;
import com.main19.server.sse.repository.EmitterRepositoryImpl;
import com.main19.server.sse.repository.SseRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.main19.server.member.entity.Member;

@Service
@RequiredArgsConstructor
public class SseService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepositoryImpl emitterRepository;
    private final SseRepository sseRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) {

        String id = userId + "_" + System.currentTimeMillis();

        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userId));
            events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                .id(id)
                .name("sse")
                .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    public void send(Member receiver, SseType sseType, String content) {
        Sse sse = createSse(receiver, sseType, content);
        sseRepository.save(sse);
        String id = String.valueOf(receiver.getMemberId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(id);
        sseEmitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, sse);
                sendToClient(emitter, key, content);
            }
        );
    }

    private Sse createSse(Member receiver, SseType sseType, String content) {
        return Sse.builder()
            .receiver(receiver)
            .content(content)
            .sseType(sseType)
            .isRead(false)
            .build();
    }
}


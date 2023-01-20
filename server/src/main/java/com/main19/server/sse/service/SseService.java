package com.main19.server.sse.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.comment.entity.Comment;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.posting.entity.Posting;
import com.main19.server.sse.entity.Sse;
import com.main19.server.sse.entity.Sse.SseType;
import com.main19.server.sse.mapper.SseMapper;
import com.main19.server.sse.repository.EmitterRepositoryImpl;
import com.main19.server.sse.repository.SseRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.main19.server.member.entity.Member;

@Service
@RequiredArgsConstructor
public class SseService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepositoryImpl emitterRepository;
    private final SseRepository sseRepository;
    private final SseMapper sseMapper;
    private final JwtTokenizer jwtTokenizer;

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

    public void send(Member receiver, SseType sseType, Member sender) {
        Sse sse = createSse(receiver, sseType, sender);
        sseRepository.save(sse);
        String id = String.valueOf(receiver.getMemberId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(id);
        sseEmitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, sse);
                sendToClient(emitter, key, sseType);
            }
        );
    }

    public void sendPosting(Member receiver, SseType sseType, Member sender, Posting posting) {
        Sse sse = createSsePosting(receiver, sseType, sender, posting);
        sseRepository.save(sse);
        String id = String.valueOf(receiver.getMemberId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(id);
        sseEmitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, sse);
                sendToClient(emitter, key, sseMapper.sseToSseResponseDto(sse));
            }
        );
    }

    public void sendChatRoom(Member receiver, SseType sseType, Member sender) {
        Sse sse = createSse(receiver, sseType, sender);
        sse.setPosting(null);
        sseRepository.save(sse);
        String id = String.valueOf(receiver.getMemberId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(id);
        sseEmitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, sse);
                sendToClient(emitter, key, sseMapper.sseToSseResponseDto(sse));
            }
        );
    }

    private Sse createSse(Member receiver, SseType sseType, Member sender) {
        return Sse.builder()
            .receiver(receiver)
            .sseType(sseType)
            .sender(sender)
            .isRead(false)
            .build();
    }

    private Sse createSsePosting(Member receiver, SseType sseType, Member sender, Posting posting) {
        return Sse.builder()
            .receiver(receiver)
            .sseType(sseType)
            .sender(sender)
            .posting(posting)
            .isRead(false)
            .build();
    }

    public Sse updateSse(long sseId,String token) {

        if(findVerifiedSse(sseId).getReceiver().getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Sse sse = findVerifiedSse(sseId);

        sse.setRead(true);

        return sse;
    }

    public Page<Sse> findSse(long memberId, Pageable pageable) {
        return sseRepository.findSse(memberId, pageable);
    }

    public Sse findSseId(long sseId) {
        return findVerifiedSse(sseId);
    }

    public void deleteSee(long sseId, String token) {

        if(findVerifiedSse(sseId).getReceiverId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Sse sse = findVerifiedSse(sseId);
        sseRepository.delete(sse);
    }

    private Sse findVerifiedSse(long sseId){
        Optional<Sse> optionalSse = sseRepository.findById(sseId);
        Sse findSse = optionalSse.orElseThrow(()->new BusinessLogicException(
            ExceptionCode.NOTIFICATION_NOT_FOUND));
        return findSse;
    }
}


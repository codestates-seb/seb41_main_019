package com.main19.server.domain.posting.scrap.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.posting.scrap.repository.ScrapRepository;
import com.main19.server.domain.posting.service.PostingService;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.service.MemberService;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.scrap.entity.Scrap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final PostingService postingService;
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;

    public Scrap createScrap(Scrap scrap, long postingId, long memberId, String token) {

        if(memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Scrap findScrap = scrapRepository.findByMember_MemberIdAndPosting_PostingId(memberId, postingId);

        if(findScrap != null) {
            throw new BusinessLogicException(ExceptionCode.SCRAP_ALREADY_EXIST);
        }

        Posting posting = postingService.findPosting(postingId);
        Member member = memberService.findMember(memberId);

        scrap.setPosting(posting);
        scrap.setMember(member);

       return scrapRepository.save(scrap);
    }

    public void deleteScrap(long scrapId, String token) {

        Scrap findScrap = findVerifiedScrap(scrapId);

        if (findScrap.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        scrapRepository.delete(findScrap);
    }

    private Scrap findVerifiedScrap(long scrapId) {
        Optional<Scrap> optionalScrap = scrapRepository.findById(scrapId);
        Scrap findScrap =
                optionalScrap.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.SCRAP_NOT_FOUND));
        return findScrap;
    }
}

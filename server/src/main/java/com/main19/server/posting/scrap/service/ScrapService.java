package com.main19.server.posting.scrap.service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.repository.MemberRepository;
import com.main19.server.member.service.MemberService;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.repository.PostingRepository;
import com.main19.server.posting.scrap.entity.Scrap;
import com.main19.server.posting.scrap.repository.ScrapRepository;
import com.main19.server.posting.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final PostingService postingService;
    private final MemberService memberService;

    public Scrap createScrap(Scrap scrap, long postingId, long memberId) {
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

    public void deleteScrap(long scrapId) {
        Scrap findScrap = findVerifiedScrap(scrapId);
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

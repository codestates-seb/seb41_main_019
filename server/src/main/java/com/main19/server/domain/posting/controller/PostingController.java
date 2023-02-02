package com.main19.server.domain.posting.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.main19.server.domain.posting.dto.PostingPostDto;
import com.main19.server.domain.posting.mapper.PostingMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main19.server.global.dto.MultiResponseDto;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.domain.posting.dto.PostingPatchDto;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.service.PostingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final PostingMapper mapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postPosting(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestPart PostingPostDto requestBody,
        @RequestPart MultipartFile file1, @RequestPart(required = false) MultipartFile file2, @RequestPart(required = false) MultipartFile file3) {

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        multipartFiles.add(file3);

        Posting posting = postingService.createPosting(requestBody, requestBody.getMemberId(), multipartFiles, token);

        return new ResponseEntity<>(
            new SingleResponseDto<>(mapper.postingToPostingResponseDto(posting)),
            HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{posting-id}")
    public ResponseEntity updatePosting(@RequestHeader(name = "Authorization") String token,
        @PathVariable("posting-id") @Positive long postingId,
        @Valid @RequestBody PostingPatchDto requestBody) {
        Posting updatedposting = postingService.updatePosting(postingId ,requestBody, token);

        return new ResponseEntity<>(
            new SingleResponseDto<>(mapper.postingToPostingResponseDto(updatedposting)),
            HttpStatus.OK);
    }

    @GetMapping(value = "/{posting-id}")
    public ResponseEntity getPosting(@PathVariable("posting-id") @Positive long postingId) {
        Posting posting = postingService.findPosting(postingId);

        return new ResponseEntity<>(
            new SingleResponseDto<>(mapper.postingToPostingResponseDto(posting)),
            HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getPostings(@Positive @RequestParam int page,
        @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.findPostings(page - 1, size);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
            new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
            HttpStatus.OK);
    }

    @GetMapping("/follow")
    public ResponseEntity getPostingsByFollwingMember(@RequestHeader(name = "Authorization") String token,
                                            @Positive @RequestParam int page,
                                            @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.findPostingsByFollowing(page - 1, size, token);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
                HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}")
    public ResponseEntity getPostingsByMember(@PathVariable("member-id") @Positive long memberId,
                                              @Positive @RequestParam int page,
                                              @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.findPostingsByMemberId(memberId, page - 1, size);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
                HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity getPostingSortByLikes(@Positive @RequestParam int page,
                                            @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.sortPostingsByLikes(page - 1, size);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
                HttpStatus.OK);
    }

    @GetMapping("/follow/popular")
    public ResponseEntity getFollowPostingsSortByLikes(@RequestHeader(name = "Authorization") String token,
                                                   @Positive @RequestParam int page,
                                                   @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.sortFollowPostingsByLikes(page - 1, size, token);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity getPostingsByStr(@NotBlank @Length(min = 2, max = 15) @RequestParam String str,
                                           @Positive @RequestParam int page,
                                           @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.findPostingsByStrContent(page - 1, size, str);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings)
                , HttpStatus.OK);
    }

    @GetMapping("/tag/search")
    public ResponseEntity getPostingTagsByStr(@NotBlank @Length(min = 2, max = 15) @RequestParam String str,
                                           @Positive @RequestParam int page,
                                           @Positive @RequestParam int size) {
        Page<Posting> postings = postingService.findPostingsByStrTag(page - 1, size, str);
        List<Posting> content = postings.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postingsToPostingsResponseDto(content), postings),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{posting-id}")
    public ResponseEntity deletePosting(@RequestHeader(name = "Authorization") String token,
        @PathVariable("posting-id") @Positive long postingId) {

        postingService.deletePosting(postingId, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{posting-id}/medias", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postMedia(@RequestHeader(name = "Authorization") String token,
                                    @PathVariable("posting-id") @Positive long postingId,
                                    @RequestPart MultipartFile file1, @RequestPart(required = false) MultipartFile file2, @RequestPart(required = false) MultipartFile file3) {

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        multipartFiles.add(file3);

        Posting updatedPosting = postingService.addMedia(postingId, multipartFiles, token);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.postingToPostingResponseDto(updatedPosting)),
            HttpStatus.OK);
    }

    @DeleteMapping(value = "/medias/{media-id}")
    public ResponseEntity deleteMedia(@RequestHeader(name = "Authorization") String token,
        @PathVariable("media-id") @Positive long mediaId) {

        postingService.deleteMedia(mediaId, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
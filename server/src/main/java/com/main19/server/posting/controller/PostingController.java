package com.main19.server.posting.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.ffmpeg.FFmpegService;
import com.main19.server.ffmpeg.FileSystemStorageService;
import com.main19.server.member.entity.Member;
import com.main19.server.posting.dto.MediaPostDto;
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

import com.main19.server.dto.MultiResponseDto;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.posting.dto.PostingPatchDto;
import com.main19.server.posting.dto.PostingPostDto;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.mapper.PostingMapper;
import com.main19.server.posting.service.PostingService;
import com.main19.server.s3service.S3StorageService;
import com.main19.server.posting.tags.entity.PostingTags;
import com.main19.server.posting.tags.service.PostingTagsService;
import com.main19.server.posting.tags.service.TagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final S3StorageService storageService;
    private final TagService tagService;
    private final PostingTagsService postingTagsService;
    private final FileSystemStorageService fileSystemStorageService;
    private final PostingMapper mapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postPosting(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestPart PostingPostDto requestBody,
        @RequestPart MultipartFile file1, @RequestPart(required = false) MultipartFile file2, @RequestPart(required = false) MultipartFile file3) {

        fileSystemStorageService.store(file1);

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        multipartFiles.add(file3);

        List<String> mediaPaths = storageService.uploadMedia(multipartFiles, requestBody.getMemberId() ,token);

        Posting posting = postingService.createPosting(mapper.postingPostDtoToPosting(requestBody),
            requestBody.getMemberId(), mediaPaths);

        for(int i = 0; i < requestBody.getTagName().size(); i++) {
            tagService.createTag(mapper.tagPostDtoToTag(requestBody.getTagName().get(i)));
            PostingTags postingTags = mapper.postingPostDtoToPostingTag(requestBody);
            String tagName = requestBody.getTagName().get(i);
            postingTagsService.createPostingTags(postingTags, posting, tagName);
        }

        return new ResponseEntity<>(
            new SingleResponseDto<>(mapper.postingToPostingResponseDto(posting)),
            HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{posting-id}")
    public ResponseEntity updatePosting(@RequestHeader(name = "Authorization") String token,
        @PathVariable("posting-id") @Positive long postingId,
        @Valid @RequestBody PostingPatchDto requestBody) {
        requestBody.setPostingId(postingId);
        Posting updatedposting = postingService.updatePosting(
            mapper.postingPatchDtoToPosting(requestBody),token);

        for (int i = 0; i < requestBody.getTagName().size(); i++) {
            tagService.createTag(mapper.tagPostDtoToTag(requestBody.getTagName().get(i)));
            PostingTags postingTags = mapper.postingPatchDtoToPostingTag(requestBody);
            String tagName = requestBody.getTagName().get(i);
            postingTagsService.updatePostingTags(postingTags, postingId, tagName);
        }

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

    @DeleteMapping(value = "/{posting-id}")
    public ResponseEntity deletePosting(@RequestHeader(name = "Authorization") String token,
        @PathVariable("posting-id") @Positive long postingId) {

        storageService.removeAll(postingId, token);
        postingService.deletePosting(postingId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{posting-id}/medias", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postMedia(@RequestHeader(name = "Authorization") String token,
                                    @PathVariable("posting-id") @Positive long postingId,
                                    @RequestPart MediaPostDto requestBody, @RequestPart MultipartFile file1, @RequestPart(required = false) MultipartFile file2) {

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);

        List<String> mediaPaths = storageService.uploadMedia(multipartFiles, requestBody.getMemberId() ,token);

        Posting updatedPosting = postingService.addMedia(postingId, mediaPaths);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.postingToPostingResponseDto(updatedPosting)),
            HttpStatus.OK);
    }

    @DeleteMapping(value = "/medias/{media-id}")
    public ResponseEntity deleteMedia(@RequestHeader(name = "Authorization") String token,
        @PathVariable("media-id") @Positive long mediaId) {

        storageService.remove(mediaId, token);
        postingService.deleteMedia(mediaId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
package com.chals.boot.controller;

import com.chals.boot.common.BaseController;
import com.chals.boot.dto.CommentDto;
import com.chals.boot.dto.ResponseDto;
import com.chals.boot.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards/{boardId}/comments")
public class CommentController extends BaseController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> saveComment(@PathVariable Long boardId,
                                                   @Valid @RequestBody CommentDto.SaveReqBody body) {
        commentService.saveComment(boardId,
                                   body);
        return responseSuccess();
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long boardId,
                                                     @Valid @RequestBody CommentDto.UpdateReqBody body) {
        commentService.updateComment(body);
        return responseSuccess();
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long boardId,
                                                     @RequestParam Long commentId,
                                                     @RequestParam String commentWriter) {
        commentService.deleteComment(commentId,
                                     commentWriter);
        return responseSuccess();
    }

}

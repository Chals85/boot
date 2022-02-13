package com.chals.boot.controller;

import com.chals.boot.common.Status;
import com.chals.boot.dto.CommentDto;
import com.chals.boot.entity.Board;
import com.chals.boot.entity.Comment;
import com.chals.boot.repository.BoardRepository;
import com.chals.boot.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class CommentControllerTest extends BaseControllerTest {

    @Test
    @Order(1)
    public void 댓글_등록() throws Exception {
        // 데이터 생성
        CommentDto.SaveReqBody body = CommentDto.SaveReqBody.builder()
                .commentWriter("홍금보")
                .commentContent("가즈아!!!!!!!!")
                .build();

        // 댓글 등록
        mockMvc.perform(RestDocumentationRequestBuilders.post("/v1/boards/{boardId}/comments", boardId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-comment-one",
                        pathParameters(
                                parameterWithName("boardId").description("게시물 아이디")
                        ),
                        requestFields(
                                fieldWithPath("commentWriter").type(JsonFieldType.STRING).description("댓글 작성자"),
                                fieldWithPath("commentContent").type(JsonFieldType.STRING).description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 댓글 조회
        List<Comment> comments = commentRepository.findByBoard_Id(boardId);

        // 검증
        assertEquals(comments.get(1).getCommentWriter(), body.getCommentWriter());
        assertEquals(comments.get(1).getCommentContent(), body.getCommentContent());
    }

    @Test
    @Order(2)
    public void 댓글_수정() throws Exception {
        // 수정데이터
        CommentDto.UpdateReqBody body = CommentDto.UpdateReqBody.builder()
                .commentId(commentId)
                .commentWriter("차범근")
                .commentContent("수정 가즈아!!!")
                .build();

        // 댓글 수정
        mockMvc.perform(RestDocumentationRequestBuilders.put("/v1/boards/{boardId}/comments", boardId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("put-comment-one",
                        pathParameters(
                                parameterWithName("boardId").description("게시물 아이디")
                        ),
                        requestFields(
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                                fieldWithPath("commentWriter").type(JsonFieldType.STRING).description("댓글 작성자"),
                                fieldWithPath("commentContent").type(JsonFieldType.STRING).description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 댓글 조회
        Optional<Comment> resultComment = commentRepository.findById(commentId);

        // 검증
        assertTrue(resultComment.isPresent());
        assertEquals(resultComment.get().getCommentContent(), body.getCommentContent());
    }

    @Test
    @Order(3)
    public void 댓글_삭제() throws Exception {

        // 댓글 삭제
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/v1/boards/{boardId}/comments", boardId)
                    .contentType(MediaType.TEXT_HTML)
                    .queryParam("commentId", comment.getId().toString())
                    .queryParam("commentWriter", comment.getCommentWriter())
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-comment-one",
                        pathParameters(
                                parameterWithName("boardId").description("게시물 아이디")
                        ),
                        requestParameters(
                                parameterWithName("commentId").description("댓글 아이디"),
                                parameterWithName("commentWriter").description("댓글 작성자")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 댓글 조회
        Optional<Comment> resultComment = commentRepository.findById(comment.getId());

        // 검증
        assertEquals(resultComment.get().getStatus(), Status.DELETE.getStatus());
    }

}
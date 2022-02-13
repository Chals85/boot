package com.chals.boot.dto;

import com.chals.boot.common.Status;
import com.chals.boot.entity.Board;
import com.chals.boot.entity.Comment;
import lombok.*;

import javax.validation.constraints.NotNull;

public class CommentDto {

   @Data
   @Builder
   @AllArgsConstructor
   @NoArgsConstructor
   public static class SaveReqBody {
      @NotNull
      private String commentWriter;       // 댓글 작성자
      @NotNull
      private String commentContent;      // 댓글 내용

      public Comment toEntity(Board board) {
           return Comment.builder()
                   .board(board)
                   .commentWriter(this.getCommentWriter())
                   .commentContent(this.getCommentContent())
                   .status(Status.READ.getStatus())
                   .build();
      }
   }

   @Data
   @Builder
   @AllArgsConstructor
   @NoArgsConstructor
   public static class UpdateReqBody {
       @NotNull
       private Long commentId;             // 댓글 아이디
       @NotNull
       private String commentWriter;       // 댓글 작성자
       @NotNull
       private String commentContent;      // 댓글 내용
   }

}

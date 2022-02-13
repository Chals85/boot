package com.chals.boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chals.boot.common.BaseEntity;

import com.chals.boot.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	@Column(name = "comment_writer", nullable = false)
	private String commentWriter;			// 작성자
	
	@Column(name = "comment_content", nullable = false)
	private String commentContent;			// 댓글내용

	@Column(name = "status")
	private String status;					// 상태

	/**
	 * 댓글내용 수정
	 * @param commentContent 댓글내용
	 */
	public void updateComment(String commentContent) {
		this.commentContent = commentContent;
		this.updateAt = LocalDateTime.now();
	}

	/**
	 * 댓글 삭제(비활성화 처리)
	 */
	public void deleteComment() {
		this.status = Status.DELETE.getStatus();
	}

}

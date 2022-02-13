package com.chals.boot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
	private String code;		// 코드
	private String msg;			// 메시지
	private Object data;		// 데이터
}

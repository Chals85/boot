package com.chals.boot.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chals.boot.dto.ResponseDto;

public class BaseController {
	
	private ResponseEntity<ResponseDto> responseFinish(ResponseDto dto,
													   HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(dto, headers, status);
	}
	
	/**
	 * 반환 데이터가 없는 정상 요청 결과 Response
	 * 
	 * @return 결과코드
	 */
	protected ResponseEntity<ResponseDto> responseSuccess() {
		return responseSuccess(null);
	}
	
	/**
	 * 반환 데이터가 있는 정상 요청 결과 Response
	 * 
	 * @param obj 반환 데이
	 * @return 결과코드, 반환 데이터
	 */
	protected ResponseEntity<ResponseDto> responseSuccess(Object obj) {
		return responseFinish(
				ResponseDto.builder()
						.code("DONE")
						.data(obj)
						.build(),
				HttpStatus.OK
		);
	}
	
	/**
	 * 예외 발생 시 요청 결과 Response
	 * 
	 * @param code 에러코드
	 * @param msg 에러내용
	 * @param status HTTP 응답코드
	 * @return 결과코드, 에러 메시지
	 */
	protected ResponseEntity<ResponseDto> exceptionResponse(String code,
															String msg,
															HttpStatus status) {
		return responseFinish(
				ResponseDto.builder()
						.code(code)
						.msg(msg)
						.build(),
				status
		);
	}
}

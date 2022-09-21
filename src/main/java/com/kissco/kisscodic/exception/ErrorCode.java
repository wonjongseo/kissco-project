package com.kissco.kisscodic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */

    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    MISMATCH_ARGUMENT(BAD_REQUEST, "URL 파라미터를 확인해주세요"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_PASSWORD(UNAUTHORIZED, "비밀번호가 옳바르지 않습니다"),
    INVALID_AUTHENTICATION(UNAUTHORIZED, "로그인이 필요합니다"),
    NOT_FOUND_VOCA(UNAUTHORIZED, "데이터가 존재하지 않습니다"),

    INVALID_VOCA_CNT(BAD_REQUEST, "보유하신 단어 이하의 숫자를 입력해주세요"),
    INVALID_AUTH_TOKEN(BAD_REQUEST, "권한 정보가 없는 토큰입니다"),
    LESS_TEST_COUNT(BAD_REQUEST, "4개 단어 이상부터 테스트가 가능합니다."),
//    INVALID_TEST_SOURCE(BAD_REQUEST, "한국어 또는 일본어만 선택 가능합니다"),
    INVALID_TEST_START(BAD_REQUEST, "1개 이상의 숫자를 입력해주세요"),

    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_USER(CONFLICT, "이메일이 이미 존재합니다"),

    ;


    private final HttpStatus httpStatus;
    private final String detail;
}


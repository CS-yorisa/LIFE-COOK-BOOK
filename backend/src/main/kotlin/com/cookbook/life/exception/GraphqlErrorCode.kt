package com.cookbook.life.exception

enum class GraphqlErrorCode(val errorType: String, val message: String) {
    /** 기본 예외 **/
    RUNTIME_EXCEPTION("RUNTIME_EXCEPTION", "예기치 못한 에러가 발생했습니다."),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION", "시스템 오류가 발생했습니다."),
    INVALID_EXCEPTION("INVALID_EXCEPTION", "입력값이 유효하지 않습니다."),
    /** 카테고리 **/
    DEFAULT_CATEGORY_DELETE_FORBIDDEN("DELETE_FORBIDDEN","기본 카테고리는 삭제할 수 없습니다."),
    CATEGORY_NOT_FOUND("INVALID_EXCEPTION", "존재하지 않는 카테고리입니다."),

    /** 자산 **/
    INVALID_REQUIRED_PARAM("INVALID_EXCEPTION", "필수 파라미터가 누락되었습니다.")
}
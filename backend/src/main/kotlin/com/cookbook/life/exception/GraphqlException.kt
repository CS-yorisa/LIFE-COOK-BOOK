package com.cookbook.life.exception

import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation


/**
 *  공통 GraphqlException (GraphQLError 상속)
 */
class GraphqlException(private val errorCode: GraphqlErrorCode,
                       @JvmField @Suppress("INAPPLICABLE_JVM_FIELD") override val message: String? = null) : GraphQLError, RuntimeException(message) {

    // 반드시 구현해야 함
    override fun getMessage(): String = super.message ?: errorCode.message

    // 에러 위치 반환 하지 않음
    override fun getLocations(): List<SourceLocation?>? = null

    // classification 전달 대신 extension에 errorType 반환
    override fun getErrorType(): ErrorClassification? = null

    // 추가 정보
    override fun getExtensions(): Map<String, Any> {
        return mapOf(
            Pair("errorType", errorCode.errorType),
        )
    }
}
package com.cookbook.life.exception

import com.cookbook.life.config.logger
import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import jakarta.validation.ConstraintViolationException
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component
import org.springframework.validation.BindException

@Component
class GraphqlExceptionHandler : DataFetcherExceptionResolverAdapter() {

    val logger = logger()

    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError {

        logger.error("::: " + ex.message + ":::" + " 경로 : " + env.executionStepInfo.path + " 위치 : " + env.field.sourceLocation)
        return when(ex) {
            is GraphqlException -> ex
            is BindException // 사용자가 입력한 값을 보여줌
                -> GraphqlException(GraphqlErrorCode.INVALID_EXCEPTION, ex.bindingResult.fieldError?.rejectedValue.toString() + "은/는 유효하지 않은 값입니다.")
            is RuntimeException -> GraphqlException(GraphqlErrorCode.RUNTIME_EXCEPTION)
            else -> GraphqlException(GraphqlErrorCode.INTERNAL_EXCEPTION)
        }
    }
}
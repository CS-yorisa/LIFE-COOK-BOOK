package com.cookbook.life.exception

import com.cookbook.life.config.logger
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import lombok.extern.slf4j.Slf4j
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.graphql.execution.ErrorType
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    val logger = logger()

    @GraphQlExceptionHandler
    fun handleRuntimeException(ex: IllegalArgumentException, env: DataFetchingEnvironment): GraphQLError {

        logger.error("::: " + ex.message + ":::" + " 경로 : " + env.executionStepInfo.path + " 위치 : " + env.field.sourceLocation)

        return GraphqlErrorBuilder.newError()
            .message(ex.message)
            .errorType(ErrorType.BAD_REQUEST)
            .build()
    }
}
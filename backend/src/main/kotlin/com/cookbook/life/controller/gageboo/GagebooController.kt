package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.gageboo.Gageboo
import com.cookbook.life.model.gageboo.MainCategory
import com.cookbook.life.service.gageboo.GagebooService
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class GagebooController (private var gagebooService: GagebooService) {

    // 회원 가계부 조회
    @QueryMapping
    fun getGagebooById(@Argument("userId") userId: UUID, @Argument("mainCategory") mainCategory: MainCategory? = null): List<Gageboo>{
        return gagebooService.getGagebooById(userId, mainCategory)
    }

    // 회원 가계부 단건 저장
    @MutationMapping
    fun saveGageboo(@Argument("gageboo") gageboo: Gageboo): Gageboo {
        return gagebooService.saveGageboo(gageboo)
    }

    // 회원 가계부 단건 수정
    @MutationMapping
    fun updateGageboo(@Argument("gageboo") gageboo: Gageboo): Gageboo {
        return gagebooService.updateGageboo(gageboo)
    }

    // 회원 가계부 단건 삭제
    @MutationMapping
    fun deleteGageboo(@Argument("gagebooNo") gagebooNo: Int, @Argument("userId") userId: UUID): Int{
        return gagebooService.deleteGageboo(gagebooNo, userId)
    }
}
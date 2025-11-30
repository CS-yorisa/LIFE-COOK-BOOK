package com.cookbook.life.controller.gageboo

import com.cookbook.life.dto.gageboo.GagebooSaveRequest
import com.cookbook.life.dto.gageboo.GagebooSearchRequest
import com.cookbook.life.model.gageboo.gageboo.Gageboo
import com.cookbook.life.model.gageboo.gageboo.MainCategory
import com.cookbook.life.service.gageboo.GagebooService
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class GagebooController (private var gagebooService: GagebooService) {

    // 회원 가계부 조회
    @QueryMapping
    fun getGagebooById(@Argument("gagebooSearchRequest") gagebooSearchRequest: GagebooSearchRequest) : List<Gageboo>{
        return gagebooService.getGagebooById(gagebooSearchRequest)
    }

    // 회원 가계부 단건 저장
    @MutationMapping
    fun saveGageboo(@Argument("gageboo") @Valid gagebooSaveRequest: GagebooSaveRequest): Gageboo {
        return gagebooService.saveGageboo(gagebooSaveRequest)
    }

    // 회원 가계부 단건 수정
    @MutationMapping
    fun updateGageboo(@Argument("gageboo") gageboo: Gageboo): Gageboo {
        return gagebooService.updateGageboo(gageboo)
    }

    // 회원 가계부 단건 삭제
    @MutationMapping
    fun deleteGageboo(@Argument("gagebooNo") gagebooNo: Int, @Argument("memberId") memberId: UUID): Int{
        return gagebooService.deleteGageboo(gagebooNo, memberId)
    }
}
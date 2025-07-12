package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.gageboo.GagebooCategory
import com.cookbook.life.service.gageboo.GagebooCategoryService
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
@RequiredArgsConstructor

class GagebooCategoryController(private var gagebooCategoryService: GagebooCategoryService) {
    // 회원가입 시 기본 카테고리 생성
    @MutationMapping
    fun makeBasicCategory(@Argument("memberId") memberId: UUID): Int{
        return gagebooCategoryService.makeBasicCategory(memberId)
    }

    // 카테고리 조회
    @QueryMapping
    fun getGagebooCategoryById(@Argument("memberId") memberId: UUID): List<GagebooCategory> {
        return gagebooCategoryService.getGagebooCategoryById(memberId);
    }

    // 신규 카테고리 추가
    @MutationMapping
    fun saveGagebooCategory(@Valid @Argument("gagebooCategory") gagebooCategory: GagebooCategory) : GagebooCategory {
        return gagebooCategoryService.saveGagebooCategory(gagebooCategory)
    }

    /*
        유저 카테고리 수정
     */
    @MutationMapping
    fun updateGagebooCategory(@Argument("gagebooCategory") gagebooCategory: GagebooCategory) : GagebooCategory {
        return gagebooCategoryService.updateGagebooCategory(gagebooCategory)
    }

    /*
        유저 카테고리 삭제
     */
    @MutationMapping
    fun deleteGagebooCategory(@Argument("memberId") memberId: UUID, @Argument("categoryNo") categoryNo: Int):Boolean{
        return gagebooCategoryService.deleteGagebooCategory(memberId, categoryNo)
    }
}
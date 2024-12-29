package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.gageboo.GagebooCategory
import com.cookbook.life.service.gageboo.GagebooCategoryService
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
    fun makeBasicCategory(@Argument("userId") userId: UUID): Int{
        return gagebooCategoryService.makeBasicCategory(userId)
    }

    // 카테고리 조회
    @QueryMapping
    fun getGagebooCategoryById(@Argument("userId") userId: UUID): List<GagebooCategory> {
        return gagebooCategoryService.getGagebooCategoryById(userId);
    }

    // 신규 카테고리 추가
    @MutationMapping
    fun saveGagebooCategory(@Argument("gagebooCategory") gagebooCategory: GagebooCategory) : GagebooCategory {
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
    fun deleteGagebooCategory(@Argument("userId") userId: UUID, @Argument("categoryNo") categoryNo: Int):Boolean{
        return gagebooCategoryService.deleteGagebooCategory(userId, categoryNo)
    }
}
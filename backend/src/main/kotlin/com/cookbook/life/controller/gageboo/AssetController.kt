package com.cookbook.life.controller.gageboo.asset

import com.cookbook.life.model.gageboo.asset.AssetCategory
import com.cookbook.life.model.gageboo.asset.UserAsset
import com.cookbook.life.service.gageboo.AssetService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class AssetController(private var assetService: AssetService) {

    // 자산 카테고리 (공통) 조회
    @QueryMapping
    fun getAssetCommonCategoryList(): List<AssetCategory>{
        return assetService.getAssetCommonCategoryList()
    }

    // 유저 별 자산 등록
    @MutationMapping
    fun insertUserAsset(@Argument("userAsset") userAsset: UserAsset): Boolean{
        return assetService.insertUserAsset(userAsset)
    }

    // 유저의 자산 목록 조회
    @QueryMapping
    fun selectUserAsset(@Argument("userId") userId: UUID): List<UserAsset>{
        return assetService.selectUserAsset(userId)
    }

    // 유저 자산 단건 삭제
    @MutationMapping
    fun deleteUserAsset(@Argument("userId") userId: UUID, @Argument("no") no: Int):Boolean{
        return assetService.deleteUserAsset(userId, no)
    }

    // 다건 삭제도 추가 필요한가?

    // 유저 자산 전체 삭제 (회원 탈퇴)
    @MutationMapping
    fun deleteAllUserAsset(@Argument("userId") userId:UUID):Boolean{
        return assetService.deleteAllUserAsset(userId)
    }

    // 수정 범위 확인 필요
}
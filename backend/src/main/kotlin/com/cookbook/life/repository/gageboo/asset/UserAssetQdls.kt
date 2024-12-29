package com.cookbook.life.repository.gageboo.asset

import com.cookbook.life.model.gageboo.asset.QAssetCategory.assetCategory
import com.cookbook.life.model.gageboo.asset.QUserAsset.userAsset
import com.cookbook.life.model.gageboo.asset.UserAsset
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class UserAssetQdls (private val queryFactory: JPAQueryFactory){
    fun findUserAssset(userId:UUID): List<UserAsset>{
        return queryFactory.selectFrom(userAsset).fetch()
    }

    // 존재하는 AssetNo 인지 확인
    fun validationAssetNo(assetNo: Int): Boolean{
        var count: Long? = queryFactory.select(assetCategory.assetCategoryNo.count())
            .from(assetCategory)
            .where(assetCategory.assetCategoryNo.eq(assetNo)).fetchOne()

        if(count == null || count == 0L){
            return false;
        } else {
            return true;
        }
    }

    // 해당 USER 의 자산 번호 중 가장 큰 값 가져와서 + 1
    fun findUserAssetMaxNo(userId:UUID): Int {
        var maxNo: Int? = queryFactory.select(userAsset.assetNo.max())
            .from(userAsset)
            .where(userAsset.userId.eq(userId))
            .fetchOne()

        if(maxNo == null){
            return 0
        } else {
            return maxNo + 1
        }
    }
}
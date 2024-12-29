package com.cookbook.life.repository.gageboo.asset

import com.cookbook.life.model.gageboo.asset.QUserAsset.userAsset
import com.cookbook.life.model.gageboo.asset.UserAsset
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class AssetQdls (private val queryFactory: JPAQueryFactory){
    fun findUserAssset(userId:UUID): List<UserAsset>{
        return queryFactory.selectFrom(userAsset).fetch()
    }

    // 해당 USER 의 자산 번호 중 가장 큰 값 가져와서 + 1
    fun findUserMaxId(userId:UUID): Int {
        var maxNum: Int? = queryFactory.select(userAsset.assetNo.max())
            .from(userAsset)
            .where(userAsset.userId.eq(userId))
            .fetchOne()

        if(maxNum == null){
            return 0
        } else {
            return maxNum + 1
        }
    }
}
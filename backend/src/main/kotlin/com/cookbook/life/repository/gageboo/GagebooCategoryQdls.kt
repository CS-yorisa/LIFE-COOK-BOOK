package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.QGagebooCategory.gagebooCategory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class GagebooCategoryQdls (private val queryFactory: JPAQueryFactory){

    // 해당 유저의 카테고리 번호중 가장 큰 값 가져와서 + 1
    fun findUserCategoryMaxNo(userId: UUID): Int{
        var maxNo: Int? = queryFactory.select(gagebooCategory.categoryNo.max())
            .from(gagebooCategory)
            .where(gagebooCategory.userId.eq(userId))
            .fetchOne()

        if(maxNo == null){
            return 0
        } else {
            return maxNo + 1
        }
    }
}
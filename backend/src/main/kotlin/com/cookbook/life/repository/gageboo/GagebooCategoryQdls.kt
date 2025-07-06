package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.MainCategory
import com.cookbook.life.model.gageboo.gageboo.QGagebooCategory.gagebooCategory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class GagebooCategoryQdls (private val queryFactory: JPAQueryFactory){

    // 해당 유저의 카테고리 번호중 가장 큰 값 가져와서 + 1
    fun findUserCategoryMaxNo(memberId: UUID): Int{
        var maxNo: Int? = queryFactory.select(gagebooCategory.categoryNo.max())
            .from(gagebooCategory)
            .where(gagebooCategory.memberId.eq(memberId))
            .fetchOne()

        if(maxNo == null){
            return 0
        } else {
            return maxNo + 1
        }
    }

    // 유저가 해당 카테고리를 가지고 있는지 확인
    fun validationUserCategory(memberId: UUID, categoryNo: Int, mainCategory: MainCategory): Int?{
        return queryFactory.select(gagebooCategory.categoryNo)
            .from(gagebooCategory)
            .where(gagebooCategory.memberId.eq(memberId).and(gagebooCategory.categoryNo.eq(categoryNo)).and(gagebooCategory.mainCategory.eq(mainCategory))).fetchOne()
    }
}
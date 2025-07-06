package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.GagebooCategory
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GagebooCategoryRepository : JpaRepository<GagebooCategory, Int> {

    @Override
    fun findAllByMemberId(memberId: UUID): List<GagebooCategory>

    @Transactional
    fun deleteGagebooCategoriesByMemberId(memberId: UUID);

    @Transactional
    fun deleteGagebooCategoryByMemberIdAndCategoryNo(memberId: UUID, categoryNo: Int);
}
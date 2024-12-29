package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.GagebooCategory
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GagebooCategoryRepository : JpaRepository<GagebooCategory, Int> {

    @Override
    fun findAllByUserId(id: UUID): List<GagebooCategory>

    @Transactional
    fun deleteGagebooCategoriesByUserId(userId: UUID);

    @Transactional
    fun deleteGagebooCategoryByUserIdAndCategoryNo(userId: UUID, categoryNo: Int);
}
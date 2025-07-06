package com.cookbook.life.repository.gageboo.asset

import com.cookbook.life.model.gageboo.asset.AssetCategory
import com.cookbook.life.model.gageboo.enum.AssetCategoryType
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : JpaRepository<AssetCategory, Int>, KotlinJdslJpqlExecutor {
    fun findAssetCategoryByAssetCategoryNo(categoryNo: Int?):AssetCategory?
}
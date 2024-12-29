package com.cookbook.life.repository.gageboo.asset

import com.cookbook.life.model.gageboo.asset.UserAsset
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserAssetRepository : JpaRepository<UserAsset, Int>, KotlinJdslJpqlExecutor {

    fun findAllByUserId(id: UUID):List<UserAsset>

    @Transactional
    fun deleteUserAssetByUserId(id: UUID)

    @Transactional
    fun deleteUserAssetByUserIdAndAssetNo(id: UUID, no:Int)

    fun findByUserIdAndAssetNo(id: UUID, assetNo: Int): UserAsset
}
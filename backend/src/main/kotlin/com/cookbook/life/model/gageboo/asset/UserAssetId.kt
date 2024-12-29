package com.cookbook.life.model.gageboo.asset

import jakarta.persistence.Column
import java.io.Serializable
import java.util.*

/* multiple PK 설정 */
data class UserAssetId(
    val userId: UUID? = UUID(0L, 0L),
    val assetNo: Int? = 0
) : Serializable

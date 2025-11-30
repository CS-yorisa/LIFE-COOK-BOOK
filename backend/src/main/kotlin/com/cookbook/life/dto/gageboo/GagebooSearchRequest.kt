package com.cookbook.life.dto.gageboo

import com.cookbook.life.model.gageboo.gageboo.MainCategory
import java.util.UUID

data class GagebooSearchRequest(
    val memberId: UUID,
    val mainCategory: MainCategory? = null,
)

package com.cookbook.life.model.gageboo.goal

import java.io.Serializable
import java.util.*

data class UserGoalId (
    val memberId: UUID? = UUID(0L, 0L),
    val goalNo: Int? = 0
) : Serializable
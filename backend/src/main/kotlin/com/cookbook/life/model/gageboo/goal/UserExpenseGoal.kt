package com.cookbook.life.model.gageboo.goal

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(schema = "gageboo")
@IdClass(UserGoalId::class)
class UserGoal (
    @Id var userId: UUID,
    @Id var goalNo: Int?,
    var period: String, //YYYYMM
    var goalAmount: Long,
){
}
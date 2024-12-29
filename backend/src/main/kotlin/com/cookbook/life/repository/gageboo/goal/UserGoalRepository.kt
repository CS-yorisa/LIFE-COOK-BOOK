package com.cookbook.life.repository.gageboo.goal

import com.cookbook.life.model.gageboo.goal.UserExpenseGoal
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserExpenseGoalRepository : JpaRepository<UserExpenseGoal, Int>, KotlinJdslJpqlExecutor {
    fun findUserGoalByUserIdAndPeriod(userId: UUID, period: String):UserExpenseGoal;
}
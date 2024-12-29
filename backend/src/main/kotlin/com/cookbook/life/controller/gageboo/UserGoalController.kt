package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.goal.UserExpenseGoal
import com.cookbook.life.service.gageboo.UserGoalService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.math.BigDecimal
import java.util.UUID

@Controller
class UserGoalController (private var userGoalService: UserGoalService) {
    // 유저별 지출 목표 조회 (월별)
    @QueryMapping
    fun getUserExpenseGoal(@Argument("userId") userId: UUID, @Argument("period") period: String?): UserExpenseGoal {
        return userGoalService.getUserExpenseGoal(userId, period)
    }

    @QueryMapping
    fun getUserExpense(@Argument("userId") userId: UUID, @Argument("period") period: String?): BigDecimal {
        return userGoalService.getUserExpense(userId, period)
    }
    // 유저별 지출 목표 등록 (월별)
    @MutationMapping
    fun saveUserExpenseGoal(@Argument("userGoal") userGoal: UserExpenseGoal): UserExpenseGoal{
        return userGoalService.saveUserExpenseGoal(userGoal)
    }

    @MutationMapping
    fun updateUserExpenseGoal(@Argument("userGoal") userGoal: UserExpenseGoal): UserExpenseGoal{
        return userGoalService.updateUserExpenseGoal(userGoal)
    }

    // 가계부 목표 등록 (지출 목표, 저축 목표)

    // 가계부 목표 조회 (기본값 : 현재 달, 퍼센테이지 함께)


    // 가계부 목표 삭제

    // 가계부 목표 수정

}
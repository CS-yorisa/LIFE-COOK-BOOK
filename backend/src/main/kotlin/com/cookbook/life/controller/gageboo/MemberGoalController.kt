package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.goal.UserExpenseGoal
import com.cookbook.life.service.gageboo.MemberGoalService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.math.BigDecimal
import java.util.UUID

@Controller
class MemberGoalController (private var memberGoalService: MemberGoalService) {
    // 유저별 지출 목표 조회 (월별)
    @QueryMapping
    fun getUserExpenseGoal(@Argument("memberId") memberId: UUID, @Argument("period") period: String?): UserExpenseGoal {
        return memberGoalService.getUserExpenseGoal(memberId, period)
    }

    // 유저 총 지출액 조회
    @QueryMapping
    fun getUserExpense(@Argument("memberId") memberId: UUID, @Argument("period") period: String?): BigDecimal {
        return memberGoalService.getUserExpense(memberId, period)
    }

    // 유저별 지출 목표 등록 (월별)
    @MutationMapping
    fun saveUserExpenseGoal(@Argument("userGoal") userGoal: UserExpenseGoal): UserExpenseGoal{
        return memberGoalService.saveUserExpenseGoal(userGoal)
    }

    // 지출 목표 수정
    @MutationMapping
    fun updateUserExpenseGoal(@Argument("userGoal") userGoal: UserExpenseGoal): UserExpenseGoal{
        return memberGoalService.updateUserExpenseGoal(userGoal)
    }

}
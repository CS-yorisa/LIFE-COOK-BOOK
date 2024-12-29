package com.cookbook.life.service.gageboo

import com.cookbook.life.model.gageboo.goal.UserExpenseGoal
import com.cookbook.life.repository.gageboo.GagebooQdls
import com.cookbook.life.repository.gageboo.goal.UserExpenseGoalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UserGoalService {

    @Autowired
    private lateinit var userExpenseGoalRepository: UserExpenseGoalRepository

    @Autowired
    private lateinit var gagebooQdls: GagebooQdls

    // 유저 목표 조회 (특정 월)
    fun getUserExpenseGoal(userId: UUID, period: String?): UserExpenseGoal {
        var yearMonth: String

        // 입력된 값이 없는 경우
        if(period == null || "".equals(period)){
            // 현재 일자의 yyyyMM 으로 설정 (기본값)
            yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        } else {
            yearMonth = period
        }

        return userExpenseGoalRepository.findUserGoalByUserIdAndPeriod(userId, yearMonth)
    }

    // 유저 지출 액수 조회
    fun getUserExpense(userId: UUID, period: String?): BigDecimal{
        var yearMonth: String

        // 입력된 값이 없는 경우
        if(period == null || "".equals(period)){
            // 현재 일자의 yyyyMM 으로 설정 (기본값)
            yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        } else {
            yearMonth = period
        }

        return gagebooQdls.selectGagebooExpenseSum(userId, yearMonth)
    }

    // 유저 목표 저장
    fun saveUserExpenseGoal(userGoal: UserExpenseGoal): UserExpenseGoal{

        return userExpenseGoalRepository.save(userGoal)
    }

    // 유저 목표 수정
    fun updateUserExpenseGoal(userGoal: UserExpenseGoal): UserExpenseGoal{
        return userExpenseGoalRepository.save(userGoal)
    }


}
package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.QGageboo.gageboo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.cglib.core.Local
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@Repository
class GagebooQdls (private val queryFactory: JPAQueryFactory){

    fun selectGagebooExpenseSum(memberId: UUID, period: String): BigDecimal {

        val formatter = DateTimeFormatter.ofPattern("yyyyMM")
        val yearMonth = YearMonth.parse(period, formatter)

        val fromDate = yearMonth.atDay(1)
        val toDate = yearMonth.atEndOfMonth()

        var expenseSum = queryFactory.select(gageboo.amounts.sum())
            .from(gageboo)
            .where(gageboo.memberId.eq(memberId).and(gageboo.date.between(fromDate, toDate))).fetchOne()

        if (expenseSum == null){
            return BigDecimal.ZERO
        } else {
            return expenseSum
        }
    }

}
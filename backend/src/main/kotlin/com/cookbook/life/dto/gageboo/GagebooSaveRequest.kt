package com.cookbook.life.dto.gageboo

import com.cookbook.life.model.gageboo.gageboo.MainCategory
import com.cookbook.life.validation.ValidEnum
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class GagebooSaveRequest (
    var memberId: UUID,
    @ValidEnum(enumClass = MainCategory::class) var categoryType: MainCategory,
    var categoryNo: Int,
    var assetNo: Int,
    var date: LocalDate,
    var content: String?,
    var amounts: BigDecimal,
    var star: Int?,
    var expenceInclude: Boolean?,
) {
    fun setting() {
        // 지출 포함 여부가 null 인 경우
        if (this.expenceInclude == null) {
            // 가계부 카테고리 타입에 따라 지출 포함 여부 설정
            this.expenceInclude = MainCategory.EXPENSES.equals(this.categoryType)
        }

        // 지출일 경우 음수로 저장, 수입일 경우 양수로 저장
        if((MainCategory.EXPENSES.equals(this.categoryType) && this.amounts.compareTo(BigDecimal.ZERO) == 1)
            || (MainCategory.INCOME.equals(this.categoryType) && this.amounts.compareTo(BigDecimal.ZERO) == -1)) {
            this.amounts = this.amounts.multiply(BigDecimal.valueOf(-1))
        }
    }
}
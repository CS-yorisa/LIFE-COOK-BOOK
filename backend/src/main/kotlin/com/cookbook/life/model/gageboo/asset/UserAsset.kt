package com.cookbook.life.model.gageboo.asset

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(schema = "gageboo")
@IdClass(UserAssetId::class)
class UserAsset(
    @Id var userId: UUID, // 유저 아이디
    @Id var assetNo: Int?, // 자산 번호
    var assetCategoryNo: Int, // 자산 카테고리 번호
    var assetName: String, // 자산 이름
    var assetAmount: BigDecimal, // 자산 총액
    var withdrawalAssetNo: Int?, // 결제 되는 자산 번호 (카드용)
    var memo: String?, //자산 메모
    var interestRate: Double?, // 이자율 (계좌일 경우)
    var isExcludeGoal: Boolean = false, // 저축 목표에 포함되는 자산인지 여부
) {
}
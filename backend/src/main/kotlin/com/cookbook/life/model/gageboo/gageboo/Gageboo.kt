package com.cookbook.life.model.gageboo.gageboo

import com.cookbook.life.validation.ValidEnum
import jakarta.persistence.*
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name="gageboo", schema = "gageboo", uniqueConstraints = [UniqueConstraint(name = "gageboo_unique_key", columnNames = ["gagebooNo", "memberId"])]) // multiple pk 대신 unique key 추가
class Gageboo (
    // GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임
    // 가계부 번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id var gagebooNo : Long?,
    // 유저 아이디
    var memberId: UUID,
    @Enumerated(EnumType.STRING) @ValidEnum(enumClass = MainCategory::class) var categoryType: MainCategory,
    var categoryNo: Int,
    var assetNo: Int,
    var date: String?,
    var content: String?,
    var amounts: BigDecimal,
    var star: Int?,
    var expenceInclude: Boolean?,
) : Serializable
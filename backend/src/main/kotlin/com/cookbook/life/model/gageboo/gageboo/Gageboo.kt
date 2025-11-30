package com.cookbook.life.model.gageboo.gageboo

import com.cookbook.life.dto.gageboo.GagebooSaveRequest
import com.cookbook.life.validation.ValidEnum
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
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
    var date: LocalDate,
    var content: String?,
    var amounts: BigDecimal,
    var star: Int?,
    var expenceInclude: Boolean?,
    @CreatedDate
    var createdAt: LocalDateTime?,
    @LastModifiedDate
    var updatedAt: LocalDateTime?,
) : Serializable {
    companion object {
        fun create(gagebooSaveRequest: GagebooSaveRequest): Gageboo {
            return Gageboo(
                gagebooNo = null,
                memberId = gagebooSaveRequest.memberId,
                categoryType = gagebooSaveRequest.categoryType,
                categoryNo = gagebooSaveRequest.categoryNo,
                assetNo = gagebooSaveRequest.assetNo,
                date = gagebooSaveRequest.date,
                content = gagebooSaveRequest.content,
                amounts = gagebooSaveRequest.amounts,
                star = gagebooSaveRequest.star,
                expenceInclude = gagebooSaveRequest.expenceInclude,
                createdAt = null,
                updatedAt = null,
            )
        }
    }
}
package com.cookbook.life.model.gageboo.gageboo

import com.cookbook.life.validation.ValidEnum
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
@Table(name="gageboo_category", schema = "gageboo", uniqueConstraints = [UniqueConstraint(name = "gageboo_category_unique_key", columnNames = ["categoryNo", "memberId"])])
// ddl-auto 이용 시에는 컬럼 순서 맘대로 지정 불가능
@IdClass(GagebooCommonId::class)
class GagebooCategory (
    @Id var categoryNo: Int?
    , @Id @field:NotNull(message = "UUID is required value") var memberId: UUID
    , @Enumerated(EnumType.STRING) @ValidEnum(enumClass = MainCategory::class) var mainCategory: MainCategory
    , var categoryName: String
)
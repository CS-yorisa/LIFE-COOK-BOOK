package com.cookbook.life.model.gageboo

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="gageboo_category", schema = "gageboo", uniqueConstraints = [UniqueConstraint(name = "gageboo_category_unique_key", columnNames = ["no", "id"])])
// ddl-auto 이용 시에는 컬럼 순서 맘대로 지정 불가능
//@IdClass(GagebooCommonId::class)
class GagebooCategory (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var no: Int?
    , var id: UUID
    , @Enumerated(EnumType.STRING) var mainCategory: MainCategory
    , var categoryName: String
)
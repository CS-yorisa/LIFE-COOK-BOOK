package com.cookbook.life.model.gageboo

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name="gageboo", schema = "gageboo", uniqueConstraints = [UniqueConstraint(name = "gageboo_unique_key", columnNames = ["no", "id"])]) // multiple pk 대신 unique key 추가
class Gageboo (
    // GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var no : Int?,
    var id: UUID?,
    var category: String?,
    var asset: String?,
    var date: String?,
    var name: String?,
    var amounts: Long?,
    var star: Int?,
    var expenceInclude: Boolean?,
) : Serializable
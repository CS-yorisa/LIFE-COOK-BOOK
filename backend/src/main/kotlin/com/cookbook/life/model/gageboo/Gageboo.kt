package com.cookbook.life.model.gageboo

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name="gageboo", schema = "gageboo")
class Gageboo (
    // GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var no : Int?
    , var id: UUID?
    , var category: String?
    , var asset: String?
    , var date: String?
    , var name: String?
    , var expence: String?
    , var star: Int?
    , var expenceInclude: Boolean?
) {

}
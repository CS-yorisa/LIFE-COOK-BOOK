package com.cookbook.life.model.gageboo.gageboo

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.io.Serializable
import java.util.*

/* multiple PK 설정 */
data class GagebooCommonId (
    @Column val no:Int = 0,
    @Column val id: UUID = UUID(0L, 0L)
) : Serializable
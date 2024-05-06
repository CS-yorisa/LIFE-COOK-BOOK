package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.Gageboo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GagebooRepository : JpaRepository<Gageboo, Int> {
    @Override
    fun findAllById(id: UUID): List<Gageboo>
}
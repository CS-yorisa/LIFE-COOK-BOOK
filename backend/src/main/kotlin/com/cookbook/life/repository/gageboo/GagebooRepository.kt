package com.cookbook.life.repository.gageboo

import com.cookbook.life.model.gageboo.gageboo.Gageboo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface GagebooRepository : JpaRepository<Gageboo, Int>, JpaSpecificationExecutor<Gageboo> {
    @Override
    fun findAllByMemberId(memberId: UUID): List<Gageboo>

    @Transactional
    fun deleteByGagebooNoAndMemberId(gagebooNo:Int, memberId:UUID): Int

    @Transactional
    fun deleteByMemberId(memberId: UUID): Int


}
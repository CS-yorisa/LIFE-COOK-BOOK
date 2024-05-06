package com.cookbook.life.service.gageboo

import com.cookbook.life.model.gageboo.Gageboo
import com.cookbook.life.repository.gageboo.GagebooRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GagebooService {
    @Autowired
    private lateinit var gagebooRepository: GagebooRepository

    fun getGagebooById(id : UUID): List<Gageboo>{
        return gagebooRepository.findAllById(id)
    }

    fun save(gageboo: Gageboo): Gageboo {
        return gagebooRepository.save(gageboo)
    }
}
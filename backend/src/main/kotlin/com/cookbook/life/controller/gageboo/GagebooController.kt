package com.cookbook.life.controller.gageboo

import com.cookbook.life.model.gageboo.Gageboo
import com.cookbook.life.service.gageboo.GagebooService
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
@RequiredArgsConstructor
class GagebooController (private var gagebooService: GagebooService) {

    @QueryMapping
    fun getGagebooById(@Argument("id") id: UUID): List<Gageboo>{
        return gagebooService.getGagebooById(id)
    }

    @MutationMapping
    fun saveGageboo(@Argument("gageboo") gageboo: Gageboo): Gageboo{
        return gagebooService.save(gageboo)
    }
}
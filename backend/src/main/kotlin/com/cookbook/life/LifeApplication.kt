package com.cookbook.life

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LifeApplication

fun main(args: Array<String>) {
	runApplication<LifeApplication>(*args)
}

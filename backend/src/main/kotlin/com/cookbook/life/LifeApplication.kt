package com.cookbook.life

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LifeApplication

fun main(args: Array<String>) {
	runApplication<LifeApplication>(*args)
}

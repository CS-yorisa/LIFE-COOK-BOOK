package com.cookbook.life.validation

import com.cookbook.life.config.logger
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EnumValidator : ConstraintValidator<ValidEnum, Any> {

    private lateinit var enumValues: Array<out Enum<*>>
    var logger = logger();

    override fun isValid(
        value: Any,
        context: ConstraintValidatorContext
    ): Boolean {
        context.disableDefaultConstraintViolation() // 기본 메시지 제거
        context.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate).addConstraintViolation()
        return enumValues.any{ it.name == value.toString() }
    }
}
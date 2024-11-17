package com.cookbook.life.dto.member

data class SingInDTO(
    var email: String,
    var passWord: String
)
//    @field:NotBlank
//    @JsonProperty("loginId")
//    private val _loginId: String?,
//    @field:NotBlank
//    @JsonProperty("password")
//    private val _password: String?
//    ) {
//        val loginId: String
//        get() = _loginId!!
//        val password: String
//        get() = _password!!
//}
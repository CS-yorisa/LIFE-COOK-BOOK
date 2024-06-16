//package com.cookbook.life.security
//
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.web.filter.OncePerRequestFilter
//// spring boot 3에는 Jakarta EE 9이 포함됨에 따라 javax 관련 패키지명이 javax에서 jakarta로 변경되었습니다. 따라서 jakarta로 사용하시는 게 정상입니다
//// https://www.inflearn.com/questions/822610/javax-persistence%EA%B0%80-import%EB%90%98%EC%A7%80-%EC%95%8A%EC%8A%B5%EB%8B%88%EB%8B%A4
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//
//class JWTAuthenticationFilter(private val jwt: JWT) : OncePerRequestFilter() {
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val token = request.getHeader("Authorization")
//        val jwtToken = extractToken(token)
//
//        if (jwt.isValid(jwtToken)) {
//            SecurityContextHolder.getContext().authentication = jwt.getAuthentication(jwtToken)
//        }
//        filterChain.doFilter(request, response)
//    }
//
//    private fun extractToken(token: String?) : String? {
//        return token?.let { jwt ->
//            jwt.startsWith("Bearer ")
//            jwt.substring(7, jwt.length)
//        }
//    }
//
//}
package com.example.kotlinsecurity.member.controller

import com.example.kotlinsecurity.common.authority.TokenInfo
import com.example.kotlinsecurity.common.dto.BaseResponse
import com.example.kotlinsecurity.common.dto.CustomUser
import com.example.kotlinsecurity.member.dto.LoginDto
import com.example.kotlinsecurity.member.dto.MemberDtoRequest
import com.example.kotlinsecurity.member.dto.MemberDtoResponse
import com.example.kotlinsecurity.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg = memberService.signUp(memberDtoRequest)

        return BaseResponse(message = resultMsg)
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)

        return BaseResponse(data = tokenInfo)
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/info")
    fun searchInfo(): BaseResponse<MemberDtoResponse> {
        return BaseResponse(data = memberService.searchMyInfo(
            (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId))
    }

    /**
     * 내 정보 수정
     */
    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberDtoRequest.id = userId

        return BaseResponse(message = memberService.saveMyInfo(memberDtoRequest))
    }
}
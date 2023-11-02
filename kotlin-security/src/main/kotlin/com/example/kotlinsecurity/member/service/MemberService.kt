package com.example.kotlinsecurity.member.service

import com.example.kotlinsecurity.common.exception.InvalidInputException
import com.example.kotlinsecurity.member.dto.MemberDtoRequest
import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {

    /**
     * 회원 가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String{
        // id 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if(member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID입니다.")
        }

        member = memberDtoRequest.toEntity()

        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }

}
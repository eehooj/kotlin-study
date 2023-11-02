package com.example.kotlinsecurity.member.service

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
            return "이미 등록된 아이디 입니다."
        }

        member = Member(
            null,
            memberDtoRequest.loginId,
            memberDtoRequest.password,
            memberDtoRequest.name,
            memberDtoRequest.birthDate,
            memberDtoRequest.email,
            memberDtoRequest.gender
        )

        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }

}
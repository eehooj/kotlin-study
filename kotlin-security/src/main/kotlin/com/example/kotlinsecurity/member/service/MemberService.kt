package com.example.kotlinsecurity.member.service

import com.example.kotlinsecurity.common.authority.JwtTokenProvider
import com.example.kotlinsecurity.common.authority.TokenInfo
import com.example.kotlinsecurity.common.exception.InvalidInputException
import com.example.kotlinsecurity.common.status.Role
import com.example.kotlinsecurity.member.dto.LoginDto
import com.example.kotlinsecurity.member.dto.MemberDtoRequest
import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.entity.MemberRole
import com.example.kotlinsecurity.member.repository.MemberRepository
import com.example.kotlinsecurity.member.repository.MemberRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
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

        val memberRole: MemberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        // authenticate 가 실행될 떄 customUserDetailedService 에 loadUserByUserName 이 호출 되면서 데이터베이스에 있는 멤버 정보와 비교
        // 아무 문제가 없다면 토큰을 발생하고 리턴
        return jwtTokenProvider.createToken(authentication)
    }
}
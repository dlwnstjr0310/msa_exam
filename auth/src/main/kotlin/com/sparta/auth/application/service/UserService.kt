package com.sparta.auth.application.service

import com.sparta.auth.domain.UserDomain
import com.sparta.auth.infrastructure.persistence.entity.Role
import com.sparta.auth.infrastructure.persistence.entity.User
import com.sparta.auth.infrastructure.persistence.entity.toDomain
import com.sparta.auth.infrastructure.persistence.repository.UserRepository
import com.sparta.auth.infrastructure.security.JwtUtil
import com.sparta.auth.presentation.api.exception.EmailAlreadyExistsException
import com.sparta.auth.presentation.api.exception.InvalidPasswordCheckException
import com.sparta.auth.presentation.api.exception.RegisteredInBlackListException
import com.sparta.auth.presentation.api.exception.TokenNotValidException
import com.sparta.auth.presentation.api.exception.UserNotFoundException
import com.sparta.auth.presentation.api.request.ModifyPasswordRequestDto
import com.sparta.auth.presentation.api.request.SignInRequestDto
import com.sparta.auth.presentation.api.request.SignUpRequestDto
import com.sparta.auth.presentation.api.response.TokenResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val jwtUtil: JwtUtil,
    private val bCrypt: BCryptPasswordEncoder,
    private val userRepository: UserRepository,
    private val redisService: RedisService,
) {

    private val accessTokenExpireTimeMillis = 60L * 60L * 1000L
    private val refreshTokenExpireTimeMillis = 14L * 24L * 60L * 60L * 1000L

    fun signUp(request: SignUpRequestDto) {
        if (userRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException()
        }

        val userDomain = UserDomain.create(
            email = request.email,
            rawPassword = request.password,
            name = request.name,
            phone = request.phone
        )

        userRepository.save(
            User(
                email = userDomain.userEmail,
                password = bCrypt.encode(userDomain.userPassword),
                name = userDomain.userName,
                phone = userDomain.userPhone,
                role = Role.USER
            )
        )
    }

    fun signIn(
        request: HttpServletRequest,
        response: HttpServletResponse,
        requestDto: SignInRequestDto,
    ): TokenResponseDto {
        val user = userRepository.findByEmail(requestDto.email) ?: throw UserNotFoundException()

        if (!bCrypt.matches(requestDto.password, user.password)) {
            throw InvalidPasswordCheckException()
        }

        val accessToken = jwtUtil.generateToken(user, accessTokenExpireTimeMillis)

        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken)

        redisService.storeRefreshToken(
            user.id.toString(),
            jwtUtil.generateToken(user, refreshTokenExpireTimeMillis),
            refreshTokenExpireTimeMillis
        )

        return jwtUtil.generateTokenResponseDto(user)
    }

    fun reissueAccessToken(request: HttpServletRequest, response: HttpServletResponse): TokenResponseDto {
        val token = jwtUtil.extractToken(request)

        val username = jwtUtil.getUsername(token)

        val refreshToken = redisService.getRefreshToken(username) ?: throw TokenNotValidException()

        if (redisService.isBlackList(refreshToken)) {
            throw RegisteredInBlackListException()
        }

        jwtUtil.validateToken(refreshToken)
        redisService.deleteRefreshToken(username, refreshTokenExpireTimeMillis)

        val user = userRepository.findByIdOrNull(username.toLong()) ?: throw UserNotFoundException()
        val accessToken = jwtUtil.generateToken(user, accessTokenExpireTimeMillis)

        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken)

        redisService.storeRefreshToken(
            user.id.toString(),
            jwtUtil.generateToken(user, refreshTokenExpireTimeMillis),
            refreshTokenExpireTimeMillis
        )

        return jwtUtil.generateTokenResponseDto(user)
    }

    fun signOut(request: HttpServletRequest) {
        val token = jwtUtil.extractToken(request)
        val username = jwtUtil.getUsername(token)

        redisService.storeInBlackList(token, accessTokenExpireTimeMillis)
        redisService.deleteRefreshToken(username, refreshTokenExpireTimeMillis)
    }

    fun modifyPassword(request: HttpServletRequest, requestDto: ModifyPasswordRequestDto) {
        val user = userRepository.findByEmail(requestDto.email) ?: throw UserNotFoundException()

        if (!bCrypt.matches(requestDto.oldPassword, user.password)) {
            throw InvalidPasswordCheckException()
        }

        val userDomain = user.toDomain()

        val updatedDomain = userDomain.changePassword(
            requestDto.newPassword
        )

        userRepository.save(
            User(
                email = updatedDomain.userEmail,
                password = bCrypt.encode(updatedDomain.userPassword),
                name = updatedDomain.userName,
                phone = updatedDomain.userPhone,
                role = Role.USER,
                id = user.id
            )
        )

        signOut(request)
    }
}

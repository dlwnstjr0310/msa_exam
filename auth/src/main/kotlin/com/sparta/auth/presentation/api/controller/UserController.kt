package com.sparta.auth.presentation.api.controller

import com.sparta.auth.application.service.UserService
import com.sparta.auth.presentation.api.controller.docs.UserControllerDocs
import com.sparta.auth.presentation.api.request.ModifyPasswordRequestDto
import com.sparta.auth.presentation.api.request.SignInRequestDto
import com.sparta.auth.presentation.api.request.SignUpRequestDto
import com.sparta.auth.presentation.api.response.Response
import com.sparta.auth.presentation.api.response.TokenResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class UserController(
    private val userService: UserService,
) : UserControllerDocs {

    @PostMapping("/sign-up")
    override fun signUp(@RequestBody request: SignUpRequestDto): Response<Unit> {
        userService.signUp(request)

        return Response(
            HttpStatus.CREATED.value(),
            HttpStatus.CREATED.reasonPhrase
        )
    }

    @PostMapping("/sign-in")
    override fun signIn(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody requestDto: SignInRequestDto,
    ): Response<TokenResponseDto> {
        return Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            userService.signIn(request, response, requestDto)
        )
    }

    @PostMapping("/token")
    override fun reissueAccessToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Response<TokenResponseDto> {
        return Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            userService.reissueAccessToken(request, response)
        )
    }

    @PostMapping("/sign-out")
    override fun signOut(
        request: HttpServletRequest,
    ): Response<Unit> {
        userService.signOut(request)

        return Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase
        )
    }

    @PatchMapping("/modify-password")
    override fun modifyPassword(
        request: HttpServletRequest,
        @RequestBody requestDto: ModifyPasswordRequestDto,
    ): Response<Unit> {
        userService.modifyPassword(request, requestDto)

        return Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase
        )
    }
}

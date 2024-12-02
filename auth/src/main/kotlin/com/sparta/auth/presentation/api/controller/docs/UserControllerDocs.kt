package com.sparta.auth.presentation.api.controller.docs

import com.sparta.auth.presentation.api.request.ModifyPasswordRequestDto
import com.sparta.auth.presentation.api.request.SignInRequestDto
import com.sparta.auth.presentation.api.request.SignUpRequestDto
import com.sparta.auth.presentation.api.response.Response
import com.sparta.auth.presentation.api.response.TokenResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping

@Tag(name = "User", description = "사용자 API")
abstract class UserControllerDocs {

    @Operation(summary = "회원가입", description = "신규 사용자 회원가입 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "회원가입 요청 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "400",
                description = """
					1. 사용중인 이메일입니다. \n
                    2. 이메일 형식이 올바르지 않습니다. \n
                    3. 사용자 이름은 2-10자 사이여야 합니다. \n
                    4. 비밀번호는 소문자와 대문자, 특수문자가 포함된 8-20자 사이여야 합니다. \n
                    5. 전화번호 형식이 올바르지 않습니다. \n
					""",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/auth/sign-up")
    abstract fun signUp(@RequestBody request: SignUpRequestDto): Response<Unit>

    @Operation(summary = "로그인", description = "사용자 로그인 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "400",
                description = "비밀번호가 일치하지 않습니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/auth/sign-in")
    abstract fun signIn(
        request: HttpServletRequest,
        response: HttpServletResponse,
        requestDto: SignInRequestDto,
    ): Response<TokenResponseDto>

    @Operation(summary = "토큰 재발급", description = "Access Token 재발급 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "토큰 재발급 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "401",
                description = """
					1.토큰이 유효하지 않습니다.\n
					2.토큰이 만료되었습니다.\n
					""",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/auth/token")
    abstract fun reissueAccessToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Response<TokenResponseDto>

    @Operation(
        summary = "로그아웃",
        description = "사용자 로그아웃 API 입니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "로그아웃 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 사용자입니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/auth/sign-out")
    abstract fun signOut(
        request: HttpServletRequest,
    ): Response<Unit>

    @Operation(summary = "비밀번호 변경", description = "사용자 비밀번호 변경 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "비밀번호 변경 성공.",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "401",
                description = "비밀번호가 일치하지 않습니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 사용자입니다.",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PatchMapping("/auth/modify-password")
    abstract fun modifyPassword(
        request: HttpServletRequest,
        @RequestBody requestDto: ModifyPasswordRequestDto,
    ): Response<Unit>
}

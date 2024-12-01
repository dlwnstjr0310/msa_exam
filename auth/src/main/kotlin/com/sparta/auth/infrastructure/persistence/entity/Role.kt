package com.sparta.auth.infrastructure.persistence.entity

enum class Role(
    key: String,
    description: String,
) {

    USER("ROLE_USER", "일반 사용자"),
    MANAGER("ROLE_MANAGER", "관리자"),
    MASTER("ROLE_MASTER", "마스터"),
}

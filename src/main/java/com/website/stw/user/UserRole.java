package com.website.stw.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"); // 사용자 권한

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}

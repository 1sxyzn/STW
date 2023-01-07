package com.website.stw.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // 로그인 시 DB에 저장된 아이디와 비밀번호를 기준으로 판단하기위한 조회
    Optional<SiteUser> findByusername(String username);
}

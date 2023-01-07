package com.website.stw.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // loadUserByUsername = 사용자명으로 비밀번호를 조회하여 리턴, 구현 필수
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("ID를 확인해주세요.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) { // ID가 admin이면 admin 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        // 해당 method는 return한 User 객체의 비밀번호가, 입력 받은 비밀번호와 일치하는지를 검사하는 로직을 내부적 가짐
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}

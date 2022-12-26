package com.clone.sansansgear.security;

import com.clone.sansansgear.entity.User;
import com.clone.sansansgear.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//인증이 완료된 사용자 추가 및 사용자의 권한 GrantedAuthority 로 추상화 및 반환, 사용자의 ID, PWD Getter
public class UserDetailsImpl implements UserDetails {

    ////인증이 완료된 사용자 추가
    private final User user; // 인증완료된 User 객체
    private final String userId; // 인증완료된 User의 ID
    private final String password; // 인증완료된 User의 PWD

    private final String username;

    public UserDetailsImpl(User user, String userId, String password, String username) {
        this.user = user;
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    //  인증완료된 User 를 가져오는 Getter
    public User getUser() {
        return user;
    }


    ////인증이 완료된 사용자 추가
    ////사용자의 권한 GrantedAuthority 로 추상화 및 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }


    ////사용자의 권한 GrantedAuthority 로 추상화 및 반환
    ////사용자의 ID, PWD Getter
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername(){ return this.username; }


//    @Override
//    public boolean getRole(){return this.role;}

    ////사용자의 ID, PWD Getter
    ////////
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
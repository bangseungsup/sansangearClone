package com.clone.sansansgear.security;

import com.clone.sansansgear.entity.User;
import com.clone.sansansgear.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service    // 빈 등록
@RequiredArgsConstructor
// UserDetailServiceImpl은 DB에서 user를 조회하고, 인증한 다음에 UserDetails를 반환하고, 이 UserDetails 사용해서 인증 겍체를 만듬
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new com.clone.sansansgear.security.UserDetailsImpl(user, user.getUserId(), user.getPassword(), user.getUsername());
    }


}
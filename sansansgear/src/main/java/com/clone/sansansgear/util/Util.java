package com.clone.sansansgear.util;

import com.clone.sansansgear.entity.Post;
import com.clone.sansansgear.entity.User;
import com.clone.sansansgear.entity.UserRoleEnum;
import com.clone.sansansgear.errorcode.CommonErrorCode;
import com.clone.sansansgear.errorcode.UserErrorCode;
import com.clone.sansansgear.exception.RestApiException;
import com.clone.sansansgear.jwt.JwtUtil;
import com.clone.sansansgear.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class Util {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User getUser(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
                System.out.println(">>>>>>>>>>>>>>" + claims.get("auth"));
            } else {
                throw new RestApiException(UserErrorCode.INVALID_TOKEN);
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserId(claims.getSubject())
                    .orElseThrow(() -> new RestApiException(UserErrorCode.NO_USER));

            return user;
        } else {
            throw new RestApiException(UserErrorCode.INVALID_TOKEN);
        }
    }

    public static void checkPostUserIdByUser(User user, Post post) {
        if (user.getRole() == UserRoleEnum.USER) {
            if (!post.getUserId().equals(user.getUserId())) {
                throw new RestApiException(CommonErrorCode.INVALID_USER);
            }
        }
    }


}
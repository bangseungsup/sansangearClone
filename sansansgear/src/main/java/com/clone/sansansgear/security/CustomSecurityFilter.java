package com.clone.sansansgear.security;

import com.clone.sansansgear.errorcode.UserErrorCode;
import com.clone.sansansgear.exception.RestApiException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSecurityFilter extends OncePerRequestFilter {        //OncePerRequestFilter : 기존에 스프링이 가지고 있던 필터

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    //생성자
    public CustomSecurityFilter(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getParameter("userId");     // getParameter를 사용하며 client 쪽에서 넘어오는 parameter 값을 가지고 올 수 있음
        String password = request.getParameter("password");

        System.out.println("userId = " + userId);
        System.out.println("password = " + password);
        System.out.println("request.getRequestURI() = " + request.getRequestURI());


        if(userId != null && password  != null && (request.getRequestURI().equals("/api/user/login") || request.getRequestURI().equals("/api/test-secured"))){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId); // db에서 username 확인해서 들고옴

            // 비밀번호 확인
            if(!passwordEncoder.matches(password, userDetails.getPassword())) { // 일반 password , db저장된 encoding password 비교
                throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
            }

            // 인증 객체 생성 및 등록
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        // filterChain을 이용해 다음 filter로 이동. 로직 맨 앞 FilterChain을 타고 온 데이터를 다음 Filter로 보내주기 위함
        // 예외 발생 시, 이전 필터로 돌아감
        filterChain.doFilter(request,response);
    }
}
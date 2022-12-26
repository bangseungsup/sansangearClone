package com.clone.sansansgear.config;


import com.clone.sansansgear.handler.CustomAccessDeniedHandler;
import com.clone.sansansgear.jwt.JwtUtil;
import com.clone.sansansgear.security.CustomAuthenticationEntryPoint;
import com.clone.sansansgear.security.CustomSecurityFilter;
import com.clone.sansansgear.security.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;

    // RequiredArgsConstructor
    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler,
            JwtUtil jwtUtil){
        this.userDetailsService = userDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {  // 아래 SecurityFilterChain 우선적으로 거치는 곳
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()      // 아래 permitAll 설정되어 있는 것들의 인증 처리를 무시하고 허용
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/api/user/**").permitAll()
                .antMatchers("api/postList").permitAll()
                .antMatchers("api/postList/{postId}").permitAll()
                .antMatchers("api/bucket").permitAll()  // 비어있는 장바구니를 확인 할 수도 있기 때문에 ----> "장바구니가 비어있습니다." 메시지
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()     // 위 web.ignoring 에서 해결
                .anyRequest().authenticated();  // 이외에 모든 요청들은 인증 처리를 하겠다

        // 로그인 사용 (formLogin) / 인증이 되지 않은 요청을 로그인 페이지로 보낼 때, 기존의 로그인 페이지가 아니라 커스터마이징한 로그인 페이지로 반환, 아래 URL 확인할 것
        http.formLogin().loginPage("/api/user/login").permitAll();

        // Custom Filter 등록하기
        http.addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);

        //접근 제한 페이지 이동 설정
        // http.exceptionHandling().accessDeniedPage("/api/user/forbidden")
        //                .and()
        //                .cors();

        // 403 Error 처리, 인증과는 별개로 추가적인 권한이 충족되지 않는 경우
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .cors();


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:3000");

        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        config.addAllowedMethod("*");

        config.addAllowedHeader("*");

        config.setAllowCredentials(true);

        config.validateAllowCredentials();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;

    }

}



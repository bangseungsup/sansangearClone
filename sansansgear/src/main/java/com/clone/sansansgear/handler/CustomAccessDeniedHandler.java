package com.clone.sansansgear.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.clone.sansansgear.errorcode.SecurityExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final SecurityExceptionCode exceptionDto =
            new SecurityExceptionCode(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException{

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 403과 관련된 status 코드와 그 값을 넣어줌

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, exceptionDto);
            os.flush();
        }
    }
}
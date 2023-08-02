//package com.vvs.murator.auth.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jparest.practice.common.error.ErrorCode;
//import jparest.practice.common.error.ErrorResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
//    private final ObjectMapper objectMapper;
//
//    // 인증 실패 Handler
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
//        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_ENTRYPOINT);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(errorResponse.getStatus());
//        try (OutputStream os = response.getOutputStream()) {
//            objectMapper.writeValue(os, errorResponse);
//            os.flush();
//        }
//    }
//}

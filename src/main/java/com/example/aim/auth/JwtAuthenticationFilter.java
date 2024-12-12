package com.example.aim.auth;

import com.example.aim.auth.cookie.CookieUtil;
import com.example.aim.member.application.MemberService;
import com.example.aim.member.application.dto.request.MemberLoginRequestDto;
import com.example.aim.member.application.dto.response.MemberResponseDto;
import com.example.aim.member.exception.error.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl("/api/v1/members/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter.attemptAuthentication");

        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequestDto memberLoginRequestDto = null;
        try {
            memberLoginRequestDto = objectMapper.readValue(request.getInputStream(), MemberLoginRequestDto.class);
        } catch (Exception e) {
            // no login request dto
            log.info("no login request dto");
            throw new UnAuthorizedException("no login request dto");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getUsername(), memberLoginRequestDto.getPassword());
        Authentication authentication = null;
        authentication = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter.successfulAuthentication");

        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();

        String accessToken = jwtProvider.generateAccessToken(memberDetails.getUsername());
        String refreshToken = jwtProvider.generateRefreshToken(memberDetails.getUsername());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        CookieUtil.addCookie(response, "refreshToken", refreshToken, jwtProvider.REFRESH_TOKEN_EXPIRATION_TIME);

        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        MemberResponseDto.builder()
                                .accessToken(accessToken)
                                .build()
                )
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter.unsuccessfulAuthentication");
        log.info("full url = " + request.getRequestURL().toString() + "?" + request.getQueryString());

        throw new UnAuthorizedException("no login request dto");
    }
}
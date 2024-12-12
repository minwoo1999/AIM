package com.example.aim.auth;

import com.example.aim.member.application.MemberService;
import com.example.aim.member.domain.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter.doFilterInternal");
        String path = request.getServletPath();
        String header = jwtProvider.getHeader(request);
        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        username = jwtProvider.getUsername(request);
        if (username != null) {
            UserEntity member = memberService.findByUsername(username).get();
            MemberDetails memberDetails = new MemberDetails(member);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
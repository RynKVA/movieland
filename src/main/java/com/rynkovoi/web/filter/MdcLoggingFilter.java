package com.rynkovoi.web.filter;

import com.rynkovoi.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class MdcLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put("userId", getUserEmail());
            MDC.put("reqId", UUID.randomUUID().toString());

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "empty";
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        }

        User principal = (User) authentication.getPrincipal();
        return principal.getId().toString();
    }
}

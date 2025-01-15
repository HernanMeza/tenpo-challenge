package com.tenpo.transaction.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.transaction.exception.GlobalCustomHandlerException;
import com.tenpo.transaction.exception.TooManyRequestException;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter implements Filter {

    private final Bucket bucket;

    private final GlobalCustomHandlerException globalCustomHandlerException;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getRequestURI().toLowerCase().contains("/tenpo/api/v1/transaction")) {

            if (bucket.tryConsume(1)) {
                chain.doFilter(request, response); // Pasa el filtro
            } else {

                String jsonTooMany = new ObjectMapper().writeValueAsString(globalCustomHandlerException.handleTooManyRequests());
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(429); // Return 429 if rate limit is exceeded
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpResponse.getWriter().write(jsonTooMany);

            }

        } else {
            chain.doFilter(request, response); // Pasa el filtro
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
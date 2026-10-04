package com.ayush.RateLimiterTestApp;

import com.ayush.rateLimiter.RateLimiterClient;
import com.ayush.rateLimiter.Response;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class testFilter implements Filter {

    @Value("${rate-limiter.url}")
    private String backendUrl;

    @Value("${rate-limiter.apikey}")
    private String apiKey;

    @Value("${rate-limiter.policies.test}")
    private String policyId;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;



        String endPoint = httpRequest.getRequestURI();
        String userIp = httpRequest.getRemoteAddr();

        RateLimiterClient client = new RateLimiterClient(backendUrl, apiKey);

        Response returnedResponse;
        try {
            returnedResponse = client.invokeRateLimit(policyId, userIp);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(returnedResponse.getAllowed()){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        else{
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Rate Limit Exceeded");
        }

    }
}

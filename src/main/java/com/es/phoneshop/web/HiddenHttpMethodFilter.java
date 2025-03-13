package com.es.phoneshop.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HiddenHttpMethodFilter extends HttpFilter {
    private static final List<String> ALLOWED_METHODS =
            List.of(HttpMethod.PUT.name(), HttpMethod.DELETE.name());

    public static final String DEFAULT_METHOD_PARAM = "_method";

    private String methodParam = DEFAULT_METHOD_PARAM;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest requestToUse = request;

        if ("POST".equals(request.getMethod())) {
            String paramValue = request.getParameter(this.methodParam);
            if (paramValue != null && !paramValue.isEmpty()) {
                String method = paramValue.toUpperCase(Locale.ROOT);
                if (ALLOWED_METHODS.contains(method)) {
                    requestToUse = new HttpMethodRequestWrapper(request, method);
                }
            }
        }

        filterChain.doFilter(requestToUse, response);
    }

    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
        private final String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }
}

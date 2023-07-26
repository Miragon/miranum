/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The class adds a {@link HttpHeaders#CACHE_CONTROL} header to each http response, if
 * the header is not already set.
 */
@Configuration
public class CacheControlConfiguration {

    private static final String CACHE_CONTROL_HEADER_VALUES = "no-cache, no-store, must-revalidate";

    @Bean
    public FilterRegistrationBean<CacheControlFilter> cacheControlFilter() {
        FilterRegistrationBean<CacheControlFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CacheControlFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * The concrete implementation of the cache control filter
     * which adds a {@link HttpHeaders#CACHE_CONTROL} to a http response,
     * if the header is not already set.
     */
    public static class CacheControlFilter extends OncePerRequestFilter {

        /**
         * The method which adds the {@link HttpHeaders#CACHE_CONTROL} header
         * to the {@link HttpServletResponse} given in the parameter,
         * if the header is not already set.
         *
         * Same contract as for {@code super.doFilter}, but guaranteed to be
         * just invoked once per request within a single request thread.
         * See {@link OncePerRequestFilter#shouldNotFilterAsyncDispatch()} for details.
         * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
         * default ServletRequest and ServletResponse ones.
         */
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            final String cacheControlHeaderValue = response.getHeader(HttpHeaders.CACHE_CONTROL);
            if (StringUtils.isBlank(cacheControlHeaderValue)) {
                response.addHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_HEADER_VALUES);
            }

            filterChain.doFilter(request, response);

        }

    }

}

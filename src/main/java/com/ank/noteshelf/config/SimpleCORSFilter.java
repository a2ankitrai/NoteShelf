package com.ank.noteshelf.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

  @Override
  public void init(FilterConfig fc) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) resp;
    HttpServletRequest request = (HttpServletRequest) req;

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
        "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, X-Auth-Token");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, resp);
    }

  }

  @Override
  public void destroy() {}



  // @Bean
  // public FilterRegistrationBean<CorsFilter> corsFilter() {
  // UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  // CorsConfiguration config = new CorsConfiguration();
  //
  // config.setAllowCredentials(true);
  // config.addAllowedOrigin("http://localhost:5000/");
  // config.addAllowedHeader("Access-Control-Allow-Origin");
  // config.addAllowedMethod("*");
  // source.registerCorsConfiguration("/**", config);
  // //config.addExposedHeader("OPTIONS");
  // FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new
  // CorsFilter(source));
  // bean.setOrder(0);
  // return bean;
  // }
  //
  // @Bean
  // public CorsFilter corsFilter() {
  // UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  // CorsConfiguration config = new CorsConfiguration();
  //
  // config.setAllowCredentials(true);
  // config.addAllowedOrigin("http://localhost:4200");
  // config.addAllowedHeader("*");
  // config.addAllowedMethod("OPTIONS");
  // config.addAllowedMethod("GET");
  // config.addAllowedMethod("POST");
  // config.addAllowedMethod("PUT");
  // config.addAllowedMethod("DELETE");
  // source.registerCorsConfiguration("/**", config);
  // return new CorsFilter(source);
  // }
}

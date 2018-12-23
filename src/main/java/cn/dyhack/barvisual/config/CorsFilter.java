package cn.dyhack.barvisual.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;  
 @Configuration
public class CorsFilter extends OncePerRequestFilter {  
  
 @Override  
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException {  
    response.addHeader("Access-Control-Allow-Origin", "*");  
    response.addHeader("Access-Control-Allow-Methods",  
            "GET, POST, PUT, DELETE, OPTIONS");  
    response.addHeader("Access-Control-Allow-Headers",  
            "origin, content-type, accept, x-requested-with, sid, mycustom, smuser");  
        filterChain.doFilter(request, response);  
    }  
}  




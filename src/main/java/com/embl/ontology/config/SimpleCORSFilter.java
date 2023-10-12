package com.embl.ontology.config;


import java.io.IOException;



import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(value=0)
public class SimpleCORSFilter implements Filter
{

   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
   {
	  HttpServletRequest request = (HttpServletRequest) req; 
	   
      HttpServletResponse response = (HttpServletResponse) res;
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept ");
      
      if (request.getMethod().equals(HttpMethod.OPTIONS.name()) 
				&& request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null) { 

			return; 
      }
      
      chain.doFilter(req, res);
   }

   public void init(FilterConfig filterConfig)
   {
   }

   public void destroy()
   {
   }

}
package com.chirag.spring.experiment.filter;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomFilter  implements Filter{

	/*
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException { try { BufferedReader reader =
	 * request.getReader(); int data; while((data = reader.read()) != -1) {
	 * System.out.print((char) data); } }catch (Exception e) { e.printStackTrace();
	 * }
	 * 
	 * }
	 */

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * try { BufferedReader reader = request.getReader(); int data; while((data =
		 * reader.read()) != -1) { System.out.print((char) data); } }catch (Exception e)
		 * { e.printStackTrace(); }
		 */
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

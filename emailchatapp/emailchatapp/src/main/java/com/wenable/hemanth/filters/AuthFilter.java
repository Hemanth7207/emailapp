package com.wenable.hemanth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.wenable.hemanth.controllers.UserController;
import com.wenable.hemanth.utils.TokenUtils;

@Component
public class AuthFilter extends OncePerRequestFilter
{

    private String TOKEN=TokenUtils.JWT_SECRET;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		List<String> exempted = new ArrayList<String>();
		exempted.add("/swagger");
		exempted.add("/webjars");
		exempted.add("/images");
		exempted.add("/v2");
		exempted.add("/csrf");
		exempted.add("/register");
		exempted.add("/login");
		boolean isurlAllowed = false;
		String url = request.getRequestURI();

		for (String string : exempted) {
			if (url.contains(string)) {
				isurlAllowed = true;
			}
		}

		if (isurlAllowed) {
			filterChain.doFilter(request, response);
			response.getStatus();
		} else {
			if (request.getHeader("Authorization").equals(TOKEN)) {
				filterChain.doFilter(request, response);
				response.getStatus();
			} else if (request.getHeader("Authorization") != TOKEN) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "token value is not correct");
			}
		}

	}
}

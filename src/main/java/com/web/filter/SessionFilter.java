package com.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter
{
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException
  {
    String strUri = request.getRequestURI();

    if ((strUri.indexOf("login") == -1) && (strUri.indexOf("userRegist") == -1) && (strUri.indexOf("loginByWeChatId") == -1) &&
            (strUri.indexOf("getCompanyList") == -1))
    {
      if (request.getSession().getAttribute("userSession") == null) {
        response.getOutputStream().write("{\"result\":{\"retcode\":-100,\"retmsg\":\"tologin\"}}".getBytes());
        return; }
      filterChain.doFilter(request, response);
      return;
    }

    filterChain.doFilter(request, response);
    return;
  }
}
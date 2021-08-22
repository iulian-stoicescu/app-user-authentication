package com.example.appuserauthentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException, ServletException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      log.warn(
          "User: "
              + auth.getName()
              + " attempted to access the protected URL: "
              + request.getRequestURI());
    }

    response.sendRedirect(request.getContextPath() + "/error");
  }
}

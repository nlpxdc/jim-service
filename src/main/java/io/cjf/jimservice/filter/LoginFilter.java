package io.cjf.jimservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.cjf.jimservice.constant.GlobalConstant;
import io.cjf.jimservice.exception.ClientException;
import io.cjf.jimservice.po.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@Component
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JWTVerifier verifier;

    public LoginFilter() {
        verifier = JWT.require(GlobalConstant.algorithmHS).build();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (uri.contains("/userLogin")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String accessToken = request.getHeader("jimAccessToken");
        if (accessToken == null || accessToken.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {
            UserSession userSession = verifyAccessToken(accessToken);
            logger.info("login userId: {}", userSession.getUserId());
            request.setAttribute("currentUserId", userSession.getUserId());
        } catch (Exception ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    private UserSession verifyAccessToken(String accessToken) throws ClientException {
        DecodedJWT jwt = verifier.verify(accessToken);
        String type = jwt.getClaim("type").asString();
        if (!type.equals("Access")) {
            throw new ClientException("invalid access token");
        }
        String userId = jwt.getClaim("userId").asString();
        UserSession userSession = new UserSession();
        userSession.setUserId(userId);
        return userSession;
    }

}

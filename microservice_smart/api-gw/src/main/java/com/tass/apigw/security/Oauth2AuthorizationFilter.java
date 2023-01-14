package com.tass.apigw.security;

import com.tass.apigw.model.TassUserAuthentication;
import com.tass.apigw.utils.HttpUtil;
import com.tass.common.model.constans.AUTHENTICATION;
import com.tass.common.redis.dto.UserLoginDTO;
import com.tass.common.redis.repository.UserLoginRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class Oauth2AuthorizationFilter extends BasicAuthenticationFilter {

    UserLoginRepository userLoginRepository;
    public Oauth2AuthorizationFilter(
        AuthenticationManager authenticationManager , UserLoginRepository userLoginRepository) {
        super(authenticationManager);

        this.userLoginRepository = userLoginRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        // lay ra token


        String token = HttpUtil.getValueFromHeader(request, AUTHENTICATION.HEADER.TOKEN);

        if (StringUtils.isBlank(token)){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        Optional<UserLoginDTO> userLoginDTO = userLoginRepository.findById(token);

        if (userLoginDTO.isEmpty()){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        UserLoginDTO userLoginDTOObject = userLoginDTO.get();

//        String role = userLoginDTOObject.getRole();
//
//        String uri = request.getRequestURI();
//
//        AntPathMatcher adt = new AntPathMatcher();

//        if (adt.match(uri, "/product/findAll") || adt.match(uri, "/product/findById") || adt.match(uri, "/product/create") || adt.match(uri, "/product/update") || adt.match(uri, "/product/delete")){
//            if (!role.equals("ADMIN")) {
//                response.setContentType("application/json");
//                response.setCharacterEncoding("UTF-8");
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                return;
//            }
//        }
//        if (adt.match(uri, "/category/findAll") || adt.match(uri, "/category/findById") || adt.match(uri, "/category/create") || adt.match(uri, "/category/update") || adt.match(uri, "/category/delete")){
//            if (!role.equals("ADMIN")) {
//                response.setContentType("application/json");
//                response.setCharacterEncoding("UTF-8");
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                return;
//            }
//        }
//        if (adt.match(uri, "/createOrder")) {
//            if (role.isBlank() || role.isEmpty()) {
//                response.setContentType("application/json");
//                response.setCharacterEncoding("UTF-8");
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                return;
//            }
//        }


        UserDetailExtend userDetailExtend = new UserDetailExtend(userLoginDTOObject);

        TassUserAuthentication tassUserAuthentication = new TassUserAuthentication(userDetailExtend);

        SecurityContextHolder.getContext().setAuthentication(tassUserAuthentication);
        chain.doFilter(request, response);

    }
}

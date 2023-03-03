package com.tamanna.apigateway.constants;

import org.springframework.stereotype.Component;

@Component
public class AppConstant {

    public static final String ERROR            = "error";

    public static final String FAILED           = "failed";
    public static final String STATUS           = "status";
    public static final String MESSAGE          = "message";

    public static final Integer SC_BAD_REQUEST      = 400;

    public static final Integer SC_OK               = 200;

    public static final String REGISTRATION_PATH = "/api/v1/auth/register";

    public static final String LOGOUT_PATH = "/api/v1/auth/logout";
    public static final String[] WHITE_LIST_URL = {
            "/",
            "index",
            "/css/*",
            "/js/*",
            "/login",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

}

package com.tamanna.apigateway.constants;

public class MicroServiceConstants {

    public static final String LOGIN_MICROSERVICE = "/login-service/api/login";

    public static final String ADMIN_MICROSERVICE = "admin-service";
    public static final String BASE_API = "/api";

    public interface AdminMicroServiceConstants {
        String FETCH_CUSTOMER_BY_USERNAME = "/fetch-cutomer/{username}";
    }
}

package com.example.backend.config.constant.routes.apiv1;

import static com.example.backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

public class AuthRoutes {
    public static final String PREFIX_LOGIN = "/login";
    public static final String PREFIX_REGISTER = "/register";
    public static final String PREFIX_MY_INFO = "/me";

    public static final String PREFIX_GET_OTP = "/user/get-otp";
    public static final String  PREFIX_CHECK_EMAIL = "/user/check-email";
    public static final String  PREFIX_CHECK_USERNAME = "/user/check-username";
    public static final String  PREFIX_UPDATE_USER = "/user/update";

    public static final String LOGIN_PAM = PREFIX_API_V1.concat(PREFIX_LOGIN);
    public static final String REGISTER_PAM = PREFIX_API_V1.concat(PREFIX_REGISTER);
    public static final String MY_INFO_PAM = PREFIX_API_V1.concat(PREFIX_MY_INFO);
    public static final String GET_OTP_PAM = PREFIX_API_V1.concat(PREFIX_GET_OTP);
    public static final String CHECK_EMAIL_PAM = PREFIX_API_V1.concat(PREFIX_CHECK_EMAIL);
    public static final String CHECK_USERNAME_PAM = PREFIX_API_V1.concat(PREFIX_CHECK_USERNAME);
    public static final String UPDATE_USER_PAM = PREFIX_API_V1.concat(PREFIX_UPDATE_USER);
}

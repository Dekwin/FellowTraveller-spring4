package com.fellowtraveler.controller.auth;

/**
 * Created by igorkasyanenko on 02.03.17.
 */
//@RestController
//@RequestMapping(path = "/login")
public class LoginController {
//
//    @Autowired
//    LoginService loginService;
//    @Autowired
//    JwtService jwtService;
//
//    @SuppressWarnings("unused")
//    public LoginController() {
//        this(null, null);
//    }
//
//    @Autowired
//    public LoginController(LoginService loginService, JwtService jwtService) {
//        this.loginService = loginService;
//        this.jwtService = jwtService;
//    }
//
//    @RequestMapping(path = "",
//            method = POST,
//            produces = APPLICATION_JSON_VALUE)
//    public MinimalProfile login(@RequestBody LoginCredentials credentials,
//                                HttpServletResponse response) throws FailedToLoginException {
//
//        return loginService.login(credentials)
//                .map(minimalProfile -> {
//                    try {
//                        response.setHeader("Token", jwtService.tokenFor(minimalProfile));
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                    return minimalProfile;
//                })
//                .orElseThrow(() -> new FailedToLoginException(credentials.getSsoId()));
//    }
}
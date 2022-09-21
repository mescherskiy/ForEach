package ua.com.foreach.security.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.com.foreach.model.entity.AuthenticationProvider;
import ua.com.foreach.model.entity.CustomUser;
import ua.com.foreach.services.CustomUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {
    private final CustomUserService customUserService;

    public AuthHandler(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        var customUser = new CustomUser();
        customUser.setFirstName(oauthUser.getName());
        customUser.setEmail(oauthUser.getEmail());
        if (oauthUser.getEmail().contains("gmail.com")) {
            customUser.setAuthenticationProvider(AuthenticationProvider.GOOGLE);
        }
        customUserService.createByOauth2(customUser);
        response.sendRedirect("/auth/success");
    }
}

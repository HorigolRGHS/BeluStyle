package com.emc.belustyle.security;

import com.emc.belustyle.entity.User;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public CustomOAuth2UserService(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setGoogleId(oAuth2User.getAttribute("sub"));
            user.setFullName(oAuth2User.getAttribute("name"));
            user.setUserImage(oAuth2User.getAttribute("picture"));
            user.setEnable(true);
            user.setRole(userRoleService.findById(2));

            userService.createUser(user);
        }

        // Get user's role and add authorities for Spring Security
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));


        // Return a new DefaultOAuth2User with roles and attributes
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}

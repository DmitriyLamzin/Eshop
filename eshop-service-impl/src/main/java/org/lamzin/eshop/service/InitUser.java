package org.lamzin.eshop.service;

import org.lamzin.eshop.model.CustomAuthorities;
import org.lamzin.eshop.model.CustomUser;
import org.lamzin.eshop.service.interfaces.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitriy on 05.09.2016.
 */
@Component
public class InitUser implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new CustomAuthorities("ROLE_ADMIN"));
        authorities.add(new CustomAuthorities("ROLE_USER"));
        CustomUser user = new CustomUser("SuperAdmin", "SuperPass", authorities);
        customUserDetailsService.saveUser(user);
    }
}

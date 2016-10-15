package org.lamzin.eshop.service.interfaces;

import org.lamzin.eshop.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by Dmitriy on 05.09.2016.
 */
public interface CustomUserDetailsService extends UserDetailsService {

    UserDetails saveUser(String username, String password, Set<String> stringAuthorities);

    UserDetails saveUser(CustomUser user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

package org.lamzin.eshop.service;

import org.lamzin.eshop.dao.interfaces.GenericDao;
import org.lamzin.eshop.model.CustomAuthorities;
import org.lamzin.eshop.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitriy on 05.09.2016.
 */
@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsServiceImpl implements org.lamzin.eshop.service.interfaces.CustomUserDetailsService {

    private GenericDao<CustomUser, String> userDao;

    @Autowired
    @Qualifier("GenericDao")
    public void setPersonDao(GenericDao<CustomUser, String> genericDao){
        this.userDao = genericDao;
        this.userDao.setType(CustomUser.class);
    }

    @Transactional
    public UserDetails saveUser(String username, String password, Set<String> stringAuthorities){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (String stringAuthority : stringAuthorities){
            authorities.add(new CustomAuthorities(stringAuthority));
        }
        CustomUser customUser = new CustomUser(username, password, authorities);
        return userDao.update(customUser);
    }

    @Transactional
    public UserDetails saveUser(CustomUser user){
        return userDao.update(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findById(username);
    }

}

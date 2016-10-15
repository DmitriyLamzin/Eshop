package org.lamzin.eshop.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Dmitriy on 05.09.2016.
 */
@Entity
public class CustomAuthorities implements GrantedAuthority {

    @Id
    private String role;

    public CustomAuthorities(String role) {
        this.role = role;
    }

    public CustomAuthorities() {
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getAuthority() {
        return role;
    }
}

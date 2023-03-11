package com.project.portfolio.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDTO {

    private String token;
    private Integer userId;
    private String bearer = "Bearer";
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDTO(String token, Integer userId, String email, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userId=userId;
        this.email = email;
        this.authorities = authorities;
    }

    public JwtDTO(String token, String bearer, Integer userId, String email, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.bearer = bearer;
        this.userId=userId;
        this.email = email;
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}

package com.securitydemo.security;

import com.securitydemo.domain.UserInfo;
import com.securitydemo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

public class MyAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        String userName=authentication.getName();
        String password=(String)authentication.getCredentials();

        UserInfo userInfo=(UserInfo) myUserDetailsService.loadUserByUsername(userName);
        if(userInfo==null){
            throw new BadCredentialsException("用户名不存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePwd=bCryptPasswordEncoder.encode(password);
        if(!userInfo.getPassword().equals(encodePwd)){
            throw new BadCredentialsException("密码不正确");
        }
        Collection<? extends GrantedAuthority> authorities=userInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

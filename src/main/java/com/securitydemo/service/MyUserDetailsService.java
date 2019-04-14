package com.securitydemo.service;

import com.securitydemo.dao.SysRoleDao;
import com.securitydemo.dao.SysUserDao;
import com.securitydemo.domain.SysRole;
import com.securitydemo.domain.SysUser;
import com.securitydemo.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        SysUser sysUser=sysUserDao.findByUserName(username);
        SysRole sysRole=sysRoleDao.getRoleById(sysUser.getId());
        Long userId=sysUser.getId();
        String password=sysUser.getPassword();
        password=new BCryptPasswordEncoder().encode(password);
        String role=sysRole.getName();
        UserInfo userInfo = new UserInfo(userId, username, password, role,
                    true, true, true, true);
        return userInfo;
    }
}
